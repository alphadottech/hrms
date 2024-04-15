package com.adt.hrms.service.impl;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.repository.DocumentTypeRepo;
import com.adt.hrms.repository.EmployeeDocumentRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.adt.hrms.service.EmployeeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    @Autowired
    private EmployeeDocumentRepo employeeDocumentRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DocumentTypeRepo documentTypeRepo;
    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "jpg", "png");
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5;

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
                } else
                    return "Document type with this id doesn't exist";
            } else
                return "Employee with this id doesn't exist";
        } else
            return "Document Already Present";

    }

    @Override
    public Page<EmployeeDocument> getAllDocumentDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDocument> response = employeeDocumentRepo.findAllDocumentDetails(pageable);

        return response;
    }
}
