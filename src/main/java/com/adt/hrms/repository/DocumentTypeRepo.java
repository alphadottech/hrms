package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.DocumentType;

public interface DocumentTypeRepo extends JpaRepository<DocumentType,Integer> {
    Optional<DocumentType> findByDocumentType(String documentType);

    @Query(value ="SELECT allowed_extension FROM av_schema.document_type WHERE id= ?1",nativeQuery = true)
    String findAllowedExtensionsByDocTypeId(@Param("id") Integer id);
   
    @Query(value =" SELECT  * FROM av_schema.document_type where is_mandartory=true",nativeQuery = true)
    List<DocumentType> getByDocumentTypeData();
}
