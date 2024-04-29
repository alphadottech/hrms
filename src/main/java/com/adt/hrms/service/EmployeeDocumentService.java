package com.adt.hrms.service;

import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.model.Res;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.google.api.services.drive.Drive;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

public interface EmployeeDocumentService {

    byte[] getEmployeeDocumentById(int employeeId, int documentTypeId, HttpServletResponse resp);

    String saveDocument(EmployeeDocumentDTO documentRequest, MultipartFile doc) throws IOException;

    Page<EmployeeDocument> getAllDocumentDetails(int page, int size);

    void downloadFileFromDrive(String fileId, HttpServletResponse response) throws IOException, GeneralSecurityException;

    Res uploadFileToDrive(File file, String mimeType, String folderId, String originalFilename, Drive drive);

//    String createFolderInDrive(String folderName, Drive service) throws IOException;

        HashMap<String, Drive> createFolder(String folderName) throws GeneralSecurityException, IOException;
//    void createFolder(String folderName) throws GeneralSecurityException, IOException;
}
