package com.adt.hrms.service.impl;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.model.Res;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;


@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    @Value("${organization.folder.name}")
    private String parentFolderName;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${google.credential.path}")
    private static String serviceAccountKeyPath;

    private static final Logger log = LoggerFactory.getLogger(EmployeeDocumentServiceImpl.class);
    @Autowired
    private EmployeeDocumentRepo employeeDocumentRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DocumentTypeRepo documentTypeRepo;
    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "jpg", "png");
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    //    private static final String SERVICE_ACCOUNT_KEY_PATH = serviceAccountKeyPath;
    private final String SERVICE_ACCOUNT_KEY_PATH = getResourcePath();


    public String getResourcePath() {
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource("cred.json").getFile();
        System.out.println("File Path: " + filePath);
        return filePath;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(GoogleNetHttpTransport
                .newTrustedTransport(), JSON_FACTORY, credential)
                .build();
    }

    @Override
    public byte[] getEmployeeDocumentById(int employeeId, int documentTypeId, HttpServletResponse resp) {
        Optional<EmployeeDocument> opt = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeId(employeeId, documentTypeId);


        byte[] document = null;

        try {
            if (opt.isEmpty()) {
                System.out.println(" Document Not Available !!!");
                return document;
            } else {
                String headerKey = "Content-Disposition";
                document = opt.get().getDocument();
                String headerValue = null;
                resp.setContentType("image/jpeg");
                headerValue = "attachment;filename=" + opt.get().getDocumentType() + ".jpg";
                System.out.println(" document Downloaded Successfully !!!");
                resp.setHeader(headerKey, headerValue);
                resp.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }


    private static String getExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length > 1) {
            return parts[parts.length - 1];
        }
        return "";
    }


    @Override
    public String saveDocument(EmployeeDocumentDTO employeeDocumentDTO, MultipartFile doc) throws IOException {
        if (doc.isEmpty()) {
            return "No file selected.";
        }
        String fileName = doc.getOriginalFilename();
        String extension = getExtension(fileName);

        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            return "Invalid file extension. Only " + ALLOWED_EXTENSIONS + " allowed.";
        }

        if (doc.getSize() > MAX_FILE_SIZE) {
            return "File size exceeds limit. Maximum allowed size is 5MB.";
        }
//        String filePath = "uploads/" + fileName;
//
//        doc.transferTo(new File(filePath));
        byte[] fileBytes = doc.getBytes();
        Optional<Employee> employee = employeeRepo.findById(employeeDocumentDTO.getEmpId());
        Optional<EmployeeDocument> opt = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeId(employeeDocumentDTO.getEmpId(), employeeDocumentDTO.getDocTypeId());
        Optional<DocumentType> documentType = documentTypeRepo.findById(employeeDocumentDTO.getDocTypeId());
        if (opt.isEmpty()) {
            if (employee.isPresent()) {
                if (documentType.isPresent()) {
                    EmployeeDocument employeeDocument = new EmployeeDocument();
                    employeeDocument.setDocument(fileBytes);
                    employeeDocument.setEmpId(employeeDocumentDTO.getEmpId());
                    employeeDocument.setDocTypeId(employeeDocumentDTO.getDocTypeId());
                    employeeDocumentRepo.save(employeeDocument);
                    return "Document type saved successfully";
                } else return "Document type with this id doesn't exist";
            } else return "Employee with this id doesn't exist";
        } else return "Document Already Present";

    }

    @Override
    public Page<EmployeeDocument> getAllDocumentDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDocument> response = employeeDocumentRepo.findAllDocumentDetails(pageable);

        return response;
    }

    @Override
    public void downloadFileFromDrive(String fileId, HttpServletResponse response) throws IOException, GeneralSecurityException {

        Drive driveService = createDriveService();

        OutputStream outputStream = response.getOutputStream();
        try {
            com.google.api.services.drive.model.File file = driveService.files().get(fileId).execute();
            String mimeType = file.getMimeType();
            String fileName = file.getName();

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            Drive.Files.Get request = driveService.files().get(fileId);
            HttpResponse fileResponse = request.executeMedia();
            fileResponse.download(outputStream);
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }


    public HashMap<String, Drive> createFolder(String folderName) throws GeneralSecurityException, IOException {

        String sitFolderId = null;
        String productionFolderId = null;


//        String organizationFolderName = "AlphaDot Technologies"; // set in properties file
        HashMap<String, Drive> result = new HashMap<>();

        Drive service = createDriveService(); //cred.json --> put in properties file

        String parentFolderId = getFolderIdByName(parentFolderName, service);
        if (parentFolderId == null) {
            // Create "AlphaDot Technologies" and its subfolders
            parentFolderId = createAlphaDotTechnologiesFolder(service);
        }

        // Create or check "sit" and "production" folders inside "AlphaDot Technologies"
        if (activeProfile.equalsIgnoreCase("sit")) {
            sitFolderId = createOrCheckSubfolder("sit", parentFolderId, service);
            String sitEmployeeFolderId = createEmployeeFolder(sitFolderId, folderName, service);
        } else {
            productionFolderId = createOrCheckSubfolder("production", parentFolderId, service);
            String productionEmployeeFolderId = createEmployeeFolder(productionFolderId, folderName, service);
        }

        result.put(folderName, service);
        return result;
    }

    private String createOrCheckSubfolder(String folderName, String parentFolderId, Drive service) throws IOException {
        // Check if the folder already exists inside the parent folder
        String folderId = getFolderIdByName(folderName, service);
        if (folderId == null) {
            // Create the folder inside the parent folder if it doesn't exist
            com.google.api.services.drive.model.File folderMetadata = new com.google.api.services.drive.model.File();
            folderMetadata.setName(folderName);
            folderMetadata.setMimeType("application/vnd.google-apps.folder");
            folderMetadata.setParents(Collections.singletonList(parentFolderId));
            com.google.api.services.drive.model.File folderX = service.files().create(folderMetadata)
                    .setFields("id, webViewLink")
                    .execute();
            return folderX.getId();
        } else {
            return folderId;
        }
    }

    private String createEmployeeFolder(String environmentFolderId,
                                        String folderName, Drive service) throws IOException {
        // Check if the employee ID folder exists inside the parent folder // change name to createSubFolder
        String employeeFolderId = getFolderIdByName(folderName, service);
        if (employeeFolderId == null) {
            // Create the employee ID folder inside the parent folder if it doesn't exist
            com.google.api.services.drive.model.File employeeFolderMetadata = new com.google.api.services.drive.model.File();
            employeeFolderMetadata.setName(folderName);
            employeeFolderMetadata.setMimeType("application/vnd.google-apps.folder");
            employeeFolderMetadata.setParents(Collections.singletonList(environmentFolderId));
            com.google.api.services.drive.model.File employeeFolder = service.files().create(employeeFolderMetadata)
                    .setFields("id, webViewLink")
                    .execute();
            return employeeFolder.getId();
        } else {
            return employeeFolderId;
        }
    }

    private String createAlphaDotTechnologiesFolder(Drive service) throws IOException {
        // Create "AlphaDot Technologies" folder
        com.google.api.services.drive.model.File parentFolderMetadata = new com.google.api.services.drive.model.File();
        parentFolderMetadata.setName("AlphaDot Technologies");
        parentFolderMetadata.setMimeType("application/vnd.google-apps.folder");
        com.google.api.services.drive.model.File parentFolder = service.files().create(parentFolderMetadata)
                .setFields("id, webViewLink")
                .execute();
        return parentFolder.getId();
    }

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    @Override
    public Res uploadFileToDrive(File file, String mimeType, String folderName, String originalFilename, Drive drive) {
        Res res = new Res();
        String employeeSitFolderId = null;
        String employeeProductionFolderId = null;
        String sitFileUrl = null;
        String productionFileUrl = null;
        try {
            if (activeProfile.equalsIgnoreCase("sit")) {
//            String employeeSitFolderId = createOrCheckSubfolder(folderName,parentFolderName, drive);
//                 employeeSitFolderId = createOrCheckSubfolder(folderName, "sit", drive);
//                if (employeeSitFolderId != null) {
//                    createEmployeeFolder("sit", folderName, drive);
                com.google.api.services.drive.model.File sitUploadFile = uploadFileToFolder(file, mimeType, originalFilename, drive, employeeSitFolderId);
                sitFileUrl = "https://drive.google.com/uc?export=view&id=" + sitUploadFile.getId();
                res.setStatus(200);
                res.setMessage("File Successfully Uploaded To Drive");
                res.setUrl(sitFileUrl);
//                }else{
//                    res.setStatus(500);
//                    res.setMessage("File Not Uploaded To Drive");
//                    res.setUrl(sitFileUrl);
//                    employeeSitFolderId = createEmployeeFolder("sit", folderName, drive);
//                    com.google.api.services.drive.model.File sitUploadFile = uploadFileToFolder(file, mimeType, originalFilename, drive, employeeSitFolderId);
//                    sitFileUrl = "https://drive.google.com/uc?export=view&id=" + sitUploadFile.getId();
//                    res.setStatus(200);
//                    res.setMessage("File Successfully Uploaded To Drive");
//                    res.setUrl(sitFileUrl);
//                }
            } else {
                // String employeeProductionFolderId = createOrCheckSubfolder(folderName,parentFolderName, drive);
//                 employeeProductionFolderId = createOrCheckSubfolder(folderName,"production", drive);
//                if (employeeProductionFolderId != null) {
                com.google.api.services.drive.model.File productionUploadFile = uploadFileToFolder(file, mimeType, originalFilename, drive, employeeProductionFolderId);
                productionFileUrl = "https://drive.google.com/uc?export=view&id=" + productionUploadFile.getId();
                res.setStatus(200);
                res.setMessage("File Successfully Uploaded To Drive");
                res.setUrl(productionFileUrl);
//                }else{
//                    employeeProductionFolderId = createEmployeeFolder("production", folderName, drive);
//                    com.google.api.services.drive.model.File productionUploadFile = uploadFileToFolder(file, mimeType, originalFilename, drive, employeeProductionFolderId);
//                    productionFileUrl = "https://drive.google.com/uc?export=view&id=" + productionUploadFile.getId();
//                    res.setStatus(200);
//                    res.setMessage("File Successfully Uploaded To Drive");
//                    res.setUrl(productionFileUrl);
//                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    private com.google.api.services.drive.model.File uploadFileToFolder(File file,
                                                                        String mimeType,
                                                                        String originalFilename,
                                                                        Drive drive,
                                                                        String folderId)
            throws IOException {
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(originalFilename);
        fileMetadata.setParents(Collections.singletonList(folderId));
        FileContent mediaContent = new FileContent(mimeType, file);

        return drive.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink")
//                .setParents(Collections.singletonList("1YcHiYIw0Q7HoobJ9cu4FRjlDg2GQDsgn"))
                .execute();
    }


//    private void SetFilePermission(String fileId)
//    {
//        Permission adminPermission = new Permission
//        {
//            EmailAddress = "test@gmail.com", // email address of drive where
//                    //you want to see files
//                    Type = "user",
//                    Role = "owner"
//        };
//        var permissionRequest = _driveService.Permissions.Create(adminPermission, fileId);
//        permissionRequest.TransferOwnership = true;   // to make owner (important)
//        permissionRequest.Execute();
//        Permission globalPermission = new Permission
//        {
//            Type = "anyone",
//                    Role = "reader"
//        };
//        var globalpermissionRequest = _driveService.Permissions.Create(globalPermission, fileId);
//        globalpermissionRequest.Execute();
//    }

//    public String createFolderInDrive(String folderName, Drive service) throws IOException {
//
//
//        String folderId = null;
//        String query = "mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and trashed=false";
//        FileList result = service.files().list().setQ(query).execute();
//        List<com.google.api.services.drive.model.File> files = result.getFiles();
//        if (files != null && !files.isEmpty()) {
//            folderId = files.get(0).getName();
//        }
////        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
////        fileMetadata.setName(folderName);
////        fileMetadata.setMimeType("application/vnd.google-apps.folder");
////        com.google.api.services.drive.model.File folder = service.files().create(fileMetadata)
////                .setFields("id")
////                .execute();
//
////        return folderId.getName();
//        return folderId;
//    }

    private String getFolderIdByName(String folderName, Drive service) throws IOException {
        String folderId = null;
        String query = "mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and trashed=false";
        FileList result = service.files().list().setQ(query).execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();
        if (files != null && !files.isEmpty()) {
            folderId = files.get(0).getId();
        }
        return folderId;
    }
}

