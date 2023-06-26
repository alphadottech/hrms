package com.adt.hrms.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "project_engagement")
public class ProjectEngagement {

    @Id
    @Column(name = "project_id")
    private String projectId;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "engaged_employee")
    private String engagedEmployee;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_name")
    private String projectName;


    @Column(name = "start_date")
    private String startDate;

    @Column(name = "status")
    private boolean status;

}
