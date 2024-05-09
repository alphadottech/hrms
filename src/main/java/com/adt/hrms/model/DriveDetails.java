package com.adt.hrms.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

@Entity
@Table(catalog = "hrms_sit", schema = "employee_schema", name = "DriveDetails")
@Proxy(lazy = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveDetails {

    @Id
    @Column(name = "drive_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drive_details_seq")
    @SequenceGenerator(name = "drive_details_seq", allocationSize = 1, schema = "employee_schema")
    private int id;

    @Column(name = "driveEmailId")
    private String driveEmailId;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    @Lob
    @Column(name = "config")
    private String config;
}
