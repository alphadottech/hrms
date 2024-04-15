package com.adt.hrms.model;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

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
}
