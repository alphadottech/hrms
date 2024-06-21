package com.adt.hrms.model;

import java.time.LocalDate;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "asset")
@Proxy(lazy = false)
@Data
public class Asset {

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_seq")
	@SequenceGenerator(name = "asset_seq", allocationSize = 1, schema = "employee_schema")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "asset_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private AssetType assetType;
	private int asset_type_id;

}
