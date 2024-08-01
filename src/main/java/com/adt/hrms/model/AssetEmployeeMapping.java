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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "asset_employee_mapping")
@Proxy(lazy = false)
@Data
public class AssetEmployeeMapping {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_employee_mapping_seq")
	@SequenceGenerator(name = "asset_employee_mapping_seq", allocationSize = 1, schema = "employee_schema")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private AssetInfo AssetInfo;
	private Integer asset_id;

	@ManyToOne
	@JoinColumn(name = "empId", referencedColumnName = "EMPLOYEE_ID", nullable = false, insertable = false, updatable = false)
	private Employee employee;
	private Integer empId;

}
