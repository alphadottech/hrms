package com.adt.hrms.service;

import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.request.EmployeeDocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EmployeeDocumentService {

    byte[] getEmployeeDocumentById(int employeeId, int documentTypeId, HttpServletResponse resp);

    String saveDocument(EmployeeDocumentDTO documentRequest, MultipartFile doc) throws IOException;

    Page<EmployeeDocument> getAllDocumentDetails(int page, int size);

    String deleteDocument(int empId,int docTypeId);

    List<EmployeeDocument> getAllDocumentDetailsByEmpId(int empId);
}
