package com.adt.hrms.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(catalog = "hrms_sit", schema = "employee_schema", name = "project_revenue")
public class ProjectRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "project_revenue_seq")
    @SequenceGenerator(name = "project_revenue_seq", allocationSize = 1, schema = "employee_schema")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private ProjectEngagement projectEngagement;

    @Column(name="year")
    private String year;

    @Column(name="month")
    private String month;

    @Column(name="project_revenue")
    private double projectRevenue;

    @Column(name="resource_expense")
    private double resourceExpense;
}

