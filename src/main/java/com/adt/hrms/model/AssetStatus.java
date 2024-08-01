package com.adt.hrms.model;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "EmployeeDB", schema = "av_schema", name = "asset_status")
@Proxy(lazy = false)
@Data
public class AssetStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_status_seq")
	@SequenceGenerator(name = "asset_status_seq", allocationSize = 1, schema = "av_schema")
	@Column(name = "id")
	private Integer id;

	@Column(name = "asset_status")
	private String assetStatus;

}
