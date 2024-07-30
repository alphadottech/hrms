package com.adt.hrms.repository;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.request.EmployeeDocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeDocumentRepo  extends JpaRepository<EmployeeDocument,Integer> {

    @Query(value = "SELECT * FROM employee_schema.employee_document where emp_id = ?1 and doc_type_id= ?2", nativeQuery = true)
    Optional<EmployeeDocument> findDocumentByDocTypeIdAndEmployeeId(int employeeId, int documentTypeId);

    @Query(value = "SELECT * FROM employee_schema.employee_document where emp_id = ?1 and doc_type_id= ?2 and document_category_type= ?3", nativeQuery = true)
    Optional<EmployeeDocument> findDocumentByDocTypeIdAndEmployeeIdAndDocumentCategoryType(int employeeId, int documentTypeId,String documentCategoryType);
    
    @Query(value = "SELECT * FROM employee_schema.employee_document",
            countQuery = "SELECT count(*) FROM employee_schema.employee_document",
            nativeQuery = true)
    Page<EmployeeDocument> findAllDocumentDetails(Pageable pageable);
    @Query(value = "SELECT * FROM employee_schema.employee_document where doc_type_id= ?1", nativeQuery = true)
    Optional<EmployeeDocument> findByDocTypeId(int docTypeId);

    @Query(value = "SELECT * FROM employee_schema.employee_document where emp_id= ?1",nativeQuery = true)
    List<EmployeeDocument> findAllDocumentDetailsByEmpId(int empId);
}
