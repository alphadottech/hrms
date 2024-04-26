package com.adt.hrms.model;


import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(catalog = "hrms_sit", schema = "employee_schema", name = "EmployeeDocument")
@Proxy(lazy = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDocument {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "document", columnDefinition = "bytea")
    private byte[] document;

    @ManyToOne
    @JoinColumn(name = "empId", referencedColumnName = "EMPLOYEE_ID", nullable = false, insertable = false, updatable = false)
    private Employee employee;
    private int empId;

    @OneToOne
    @JoinColumn(name = "docTypeId",referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private DocumentType documentType;
    private int docTypeId;



}
