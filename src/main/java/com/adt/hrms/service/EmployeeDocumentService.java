package com.adt.hrms.service;

import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.google.api.services.drive.Drive;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface EmployeeDocumentService {

    String getEmployeeDocumentById(int employeeId, int documentTypeId, HttpServletResponse resp);

    String saveDocument(EmployeeDocumentDTO documentRequest, MultipartFile doc) throws IOException, TikaException, SAXException, GeneralSecurityException;

    Page<EmployeeDocument> getAllDocumentDetails(int page, int size);

    String uploadFileToDrive(File file, String mimeType, String folderId, String originalFilename, Drive drive);

    List<String> createFolder(String folderName, Drive service) throws GeneralSecurityException, IOException;

    List<EmployeeDocument> getAllDocumentDetailsByEmpId(int empId);

    String deleteDocument(int empId, int docTypeId);

    int getDocumentTypeId(String documentTypeName);
}
