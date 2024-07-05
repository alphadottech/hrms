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
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "asset_attribute_mapping")
@Proxy(lazy = false)
@Data
public class AssetAttributeMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_attribute_mapping_seq")
	@SequenceGenerator(name = "asset_attribute_mapping_seq", allocationSize = 1, schema = "employee_schema")
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private AssetInfo assetInfo;
	private Integer asset_id;

	@ManyToOne
	@JoinColumn(name = "asset_attribute_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private AssetAttribute assetAttribute;
	private Integer asset_attribute_id;

	@Column(name = "asset_attribute_value")
	private String assetAttributeValue;

}
