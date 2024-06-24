package com.adt.hrms.request;

import com.adt.hrms.model.AssetAttribute;
import com.adt.hrms.model.AssetType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO {

	private int assetTypeId;
	private int assetAttributeId;
	private String assetAttributeValue;
}
