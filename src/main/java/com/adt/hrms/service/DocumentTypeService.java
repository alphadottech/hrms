package com.adt.hrms.service;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.request.EmployeeDocumentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentTypeService {
    String saveDocumentType(DocumentType documentType);
    String deleteDocTypeById(int docTypeId);
    String updateDocumentType(DocumentType documentType);

    List<DocumentType> getDocumentTypes();
}
