package com.adt.hrms.service.impl;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.repository.DocumentTypeRepo;
import com.adt.hrms.repository.EmployeeDocumentRepo;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.adt.hrms.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepo documentTypeRepo;
    @Autowired
    private EmployeeDocumentRepo employeeDocumentRepo;

    @Override
    public String saveDocumentType(DocumentType documentType) {
        Optional<DocumentType> opt = documentTypeRepo.findByDocumentType(documentType.getDocumentType());
        if (opt.isPresent()) return "Document Type " + documentType.getDocumentType() + " is already avalable";
        return documentTypeRepo.save(documentType).getDocumentType() + "Document is Saved Successfully";
    }

    @Override
    public String deleteDocTypeById(int docTypeId) {
        Optional<DocumentType> opt = documentTypeRepo.findById(docTypeId);
        if (opt.isPresent()) {
            Optional<EmployeeDocument> doc = employeeDocumentRepo.findByDocTypeId(docTypeId);
            if (doc.isEmpty()) {
                documentTypeRepo.deleteById(docTypeId);
                return "Document with ID " + docTypeId + " has been Deleted";
            } else {
                return "Document with ID " + docTypeId + " is already in user";
            }
        } else return "Invalid Document Type Id :: " + docTypeId;


    }

    @Override
    public String updateDocumentType(DocumentType documentType) {
               if (documentType.getId() != null &&
                    documentType.getDocumentType() != null &&
                      !documentType.getDocumentType().isEmpty()) {
            Optional<DocumentType> opt = documentTypeRepo.findById(documentType.getId());
            if (opt.isPresent()) {
                opt.get().setDocumentType(documentType.getDocumentType());
                documentTypeRepo.save(opt.get());
                return "Document Type " + documentType.getDocumentType() + " has been update";
            }
        }
        return "Invalid Document Type :: " + documentType.getId();
    }

    @Override
    public List<DocumentType> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepo.findAll();
        return documentTypes;
    }

}

