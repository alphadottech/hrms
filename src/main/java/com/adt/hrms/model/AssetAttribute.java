package com.adt.hrms.model;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "EmployeeDB", schema = "av_schema", name = "asset_attribute")
@Proxy(lazy = false)
@Data
public class AssetAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_attribute_seq")
	@SequenceGenerator(name = "asset_attribute_seq", allocationSize = 1, schema = "av_schema")
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "asset_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private AssetType assetType;
	private int asset_type_id;

}
