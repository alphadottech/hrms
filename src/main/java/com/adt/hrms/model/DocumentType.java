package com.adt.hrms.model;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "hrms_sit", schema = "av_schema", name = "DocumentType")
@Proxy(lazy = false)
@Data
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="document_type")
    private String documentType;

    @Column(name = "allowed_extension")
    private String allowedExtensions;
    
    @Column(name = "is_mandartory")
    private String isMandatory;
    
    @Column(name = "category_type")
    private String categoryType;
}
