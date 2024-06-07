package com.adt.hrms.repository;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DocumentTypeRepo extends JpaRepository<DocumentType,Integer> {
    Optional<DocumentType> findByDocumentType(String documentType);

    @Query(value ="SELECT allowed_extension FROM av_schema.document_type WHERE id= ?1",nativeQuery = true)
    String findAllowedExtensionsByDocTypeId(@Param("id") Integer id);
}
