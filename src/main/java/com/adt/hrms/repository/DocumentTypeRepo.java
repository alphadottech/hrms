package com.adt.hrms.repository;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentTypeRepo extends JpaRepository<DocumentType,Integer> {
    Optional<DocumentType> findByDocumentType(String documentType);
}
