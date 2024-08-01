package com.adt.hrms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.DriveDetails;
import com.adt.hrms.model.DriveDetailsRepository;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.repository.DocumentTypeRepo;
import com.adt.hrms.repository.EmployeeDocumentRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.adt.hrms.service.EmployeeDocumentService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    @Value("${organization.folder.name}")
    private String parentFolderName;

    @Value("${allowed.extension.types}")
    private String allowedExtensions;
    @Value("${drive.parent.folder}")
    private String activeProfile;

    private static final Logger log = LoggerFactory.getLogger(EmployeeDocumentServiceImpl.class);
    @Autowired
    private EmployeeDocumentRepo employeeDocumentRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DocumentTypeRepo documentTypeRepo;

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private final String SERVICE_ACCOUNT_KEY_PATH = getResourcePath();

    private final String folderMimeType = "application/vnd.google-apps.folder";
    @Autowired
    private DriveDetailsRepository driveDetailsRepository;
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Drive createDriveService(String serviceAccountKeyJson) throws GeneralSecurityException, IOException {

        InputStream keyStream = new ByteArrayInputStream(serviceAccountKeyJson.getBytes(StandardCharsets.UTF_8));

        GoogleCredential credential = GoogleCredential
                .fromStream(keyStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(GoogleNetHttpTransport
                .newTrustedTransport(), JSON_FACTORY, credential)
                .build();
    }

    @Override
    public String saveDocument(EmployeeDocumentDTO employeeDocumentDTO, MultipartFile document) throws IOException, RuntimeException, GeneralSecurityException, TikaException, SAXException {

        if (document.isEmpty()) {
            return "No file selected.";
        }

        Optional<Employee> employee = employeeRepo.findById(employeeDocumentDTO.getEmpId());
        Optional<EmployeeDocument> opt = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeIdAndDocumentCategoryType(employeeDocumentDTO.getEmpId(), employeeDocumentDTO.getDocTypeId(),employeeDocumentDTO.getDocumentCategoryType());
        Optional<DriveDetails> driveDetails=driveDetailsRepository.findByStatus(true);
        Optional<DocumentType> documentType = documentTypeRepo.findById(employeeDocumentDTO.getDocTypeId());
        if (opt.isEmpty()) {
            if (employee.isPresent()) {
                if (documentType.isPresent()) {
                    if(driveDetails.isPresent()) {
                        String fileName = document.getOriginalFilename();
                        List<String> extensions = getAllowedExtensions(employeeDocumentDTO.getDocTypeId());
                        Parser parser = new AutoDetectParser();
                        Metadata metadata = new Metadata();
                        BodyContentHandler handler = new BodyContentHandler();
                        ParseContext context = new ParseContext();
                        parser.parse(document.getInputStream(), handler, metadata, context);
                        String mimeType = metadata.get(Metadata.CONTENT_TYPE);
                        String extension = FilenameUtils.getExtension(fileName);
                        if (!extensions.contains(extension.toLowerCase())) {
                            return "Invalid file extension. Only " + extensions + " allowed.";
                        }
                        if (document.getSize() > MAX_FILE_SIZE) {
                            return "File size exceeds limit. Maximum allowed size is 5MB.";
                        }

                        Drive driveService = createDriveService(driveDetails.get().getConfig());
                        List<String> list = createFolder(employeeDocumentDTO.getDocumentCategoryType(),String.valueOf(employeeDocumentDTO.getEmpId()), driveService);
                        String empFolderId = list.get(0);
                        File tempFile = File.createTempFile("temp", null);
                        document.transferTo(tempFile);
                        String fileId = uploadFileToDrive(tempFile, mimeType, empFolderId, document.getOriginalFilename(), driveService);

                        if (fileId != null) {
                            EmployeeDocument employeeDocument = new EmployeeDocument();
                            employeeDocument.setEmpId(employeeDocumentDTO.getEmpId());
                            employeeDocument.setDocTypeId(employeeDocumentDTO.getDocTypeId());
                            employeeDocument.setDriveDetailsId(driveDetails.get().getId());
                            employeeDocument.setFileId(fileId);
                            employeeDocument.setDocumentCategoryType(employeeDocumentDTO.getDocumentCategoryType());
                            employeeDocumentRepo.save(employeeDocument);
                            return "Document saved successfully";
                        }
                    }else
                        return "Document configuration not present";
                } else
                    return "Document type with this id doesn't exist";
            } else
                return "Employee with this id doesn't exist";
        } else
            return "Document Already Present";
        return null;
    }


    @Override
    public String getEmployeeDocumentById(int employeeId, int documentTypeId, String documentCategoryType,HttpServletResponse response) {
        Optional<EmployeeDocument> opt = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeIdAndDocumentCategoryType(employeeId, documentTypeId,documentCategoryType);
        Optional<DriveDetails> driveDetails=driveDetailsRepository.findByStatus(true);

        try {
            if (opt.isEmpty()) {
                System.out.println("Document Not Available !!!");
                return "Document Not Available !!!";
            } else if(driveDetails.isPresent()){
                Drive driveService = createDriveService(driveDetails.get().getConfig());
                OutputStream outputStream = response.getOutputStream();
                com.google.api.services.drive.model.File file = driveService.files().get(opt.get().getFileId()).execute();
                String mimeType = file.getMimeType();
                String fileName = file.getName();

                response.setContentType(mimeType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                Drive.Files.Get request = driveService.files().get(opt.get().getFileId());
                HttpResponse fileResponse = request.executeMedia();
                fileResponse.download(outputStream);

                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=\"" + opt.get().getDocumentType() + "\"";
                System.out.println(" Document Downloaded Successfully !!!");
                response.setHeader(headerKey, headerValue);
                response.flushBuffer();
                return "Document Downloaded Successfully !!!";
            }
            else {
                return "Drive configuration not present !!!";
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return "Error while downloading document !!!";
        }

    }


    @Override
    public Page<EmployeeDocument> getAllDocumentDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDocument> response = employeeDocumentRepo.findAllDocumentDetails(pageable);
        return response;
    }

    @Override
    public List<EmployeeDocument> getAllDocumentDetailsByEmpId(int empId) {
        List<EmployeeDocument> response = employeeDocumentRepo.findAllDocumentDetailsByEmpId(empId);
        return response;
    }

    @Override
    public String deleteDocument(int empId, int docTypeId,String documentCategoryType) {
        try {
            Optional<Employee> employee = employeeRepo.findById(empId);
            if (employee.isPresent()) {
                Optional<DocumentType> documentType = documentTypeRepo.findById(docTypeId);
                if (documentType.isPresent()) {
                    Optional<EmployeeDocument> opt = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeIdAndDocumentCategoryType(empId, docTypeId,documentCategoryType);
                    Optional<DriveDetails> driveDetails=driveDetailsRepository.findByStatus(true);
                    if (opt.isPresent()&&driveDetails.isPresent()) {
                        Drive driveService = createDriveService(opt.get().getDriveDetails().getConfig());
                        driveService.files().delete(opt.get().getFileId()).execute();
                        employeeDocumentRepo.deleteById(opt.get().getId());
                        return "Document Deleted Successfully";
                    } else
                        return "Document is Not Present";
                } else
                    return "Invalid Document Type";
            } else
                return "No Employee is Present With current Id";
        } catch (NullPointerException |GeneralSecurityException |IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<String> createFolder(String documentCategoryType,String folderName, Drive service) throws IOException {

        String folderId = null;
        List<String> result = new LinkedList<>();
        com.google.api.services.drive.model.File parentFolderMetadata = null;
        try {
            String parentFolderId = getFolderIdByName(parentFolderName, service, "");

            if (parentFolderId == null) {
                parentFolderMetadata = new com.google.api.services.drive.model.File();
                parentFolderMetadata.setName(parentFolderName);
                parentFolderMetadata.setMimeType(folderMimeType);
                parentFolderMetadata = service.files().create(parentFolderMetadata).setFields("id, webViewLink").execute();
                parentFolderId = parentFolderMetadata.getId();
            }
            if (activeProfile.equalsIgnoreCase("sit")) {
                folderId = createOrCheckSubfolder("sit", parentFolderId, service);
                folderId= createOrCheckSubfolder("document", folderId, service);
                
            } else {
                folderId = createOrCheckSubfolder("production", parentFolderId, service);
                folderId= createOrCheckSubfolder("document", folderId, service);
            }
            switch(documentCategoryType){  
            
            case "kyc_document": 
            	folderId= createOrCheckSubfolder("kyc_document", folderId, service); 
            break;  
            case "personal_document":
            	folderId= createOrCheckSubfolder("personal_document", folderId, service);  
            break;  
            case "professional_document":
            	folderId= createOrCheckSubfolder("professional_document", folderId, service);  
            break; 
            case "academic_document":
            	folderId= createOrCheckSubfolder("academic_document", folderId, service);  
            break;  
          }
            result.add(createOrCheckSubfolder(folderName, folderId, service));
        }catch (GoogleJsonResponseException e) {
            System.err.println("Google Drive API error: " + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return result;
    }

    private String createOrCheckSubfolder(String folderName, String parentFolderId, Drive service) throws IOException {

        String folderId = getFolderIdByName(folderName, service, parentFolderId);
        if (folderId == null) {
            com.google.api.services.drive.model.File folderMetadata = new com.google.api.services.drive.model.File();
            folderMetadata.setName(folderName);
            folderMetadata.setMimeType(folderMimeType);
            folderMetadata.setParents(Collections.singletonList(parentFolderId));
            com.google.api.services.drive.model.File folderX = service.files().create(folderMetadata).setFields("id, webViewLink").execute();
            return folderX.getId();
        } else {
            return folderId;
        }
    }


    @Override
    public String uploadFileToDrive(File file, String mimeType, String empFolderId, String originalFilename, Drive drive) {
        String fileId = null;
        com.google.api.services.drive.model.File fileMetadata = null;
        FileContent mediaContent = null;
        Permission userPermission = new Permission().setType("user").setRole("reader").setEmailAddress("alphadottechnologies@gmail.com");

        try {
            fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(originalFilename);
            fileMetadata.setParents(Collections.singletonList(empFolderId));
            mediaContent = new FileContent(mimeType, file);
            fileMetadata = drive.files().create(fileMetadata, mediaContent).setFields("id, webViewLink").execute();
            drive.permissions().create(fileMetadata.getId(), userPermission).execute();
            fileId = fileMetadata.getId();
            return fileId;
        } catch (GoogleJsonResponseException e) {
            // Handle errors returned by the Google Drive API
            System.err.println("Google Drive API returned an error: " + e.getDetails().getMessage());
        } catch (IOException e) {
            // Handle general I/O errors
            System.err.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }


    private String getFolderIdByName(String folderName, Drive service, String parentFolderId) throws IOException {
        String folderId = null;
        String query = null;
        if (parentFolderId != "") {
            query = "mimeType='" + folderMimeType + "' and name='" + folderName + "' and '" + parentFolderId + "' in parents and trashed=false";
        } else {
            query = "mimeType='" + folderMimeType + "' and name='" + folderName + "' and trashed=false";
        }
        FileList result = service.files().list().setQ(query).execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();
        if (files != null && !files.isEmpty()) {
            folderId = files.get(0).getId();
        }
        return folderId;
    }

    private List<String> getAllowedExtensions(int docTypeId) {
        String extensions = documentTypeRepo.findAllowedExtensionsByDocTypeId(docTypeId);
        if (extensions != null && !extensions.isEmpty()){
            return Arrays.asList(extensions.split(","));
        } else {
            return Arrays.asList(allowedExtensions.split(","));
        }
    }

    @Override
    public int getDocumentTypeId(String documentTypeName) {
        Optional<DocumentType> opt = documentTypeRepo.findByDocumentType(documentTypeName);
        if (opt.isPresent()) {
            return opt.get().getId();
        } else {
            throw new NoSuchElementException("Document type not found: " + documentTypeName);
        }
    }
    
    
    @Override
    public List<DocumentType> getDocumentByCategoryType(String documentCategoryType) {
    	LOGGER.info("get all category data");       
    	List<DocumentType> opt = documentTypeRepo.getByDocumentCategoryType(documentCategoryType);
    	LOGGER.info("category data:"+opt); 
        return opt;
  
    }

}


