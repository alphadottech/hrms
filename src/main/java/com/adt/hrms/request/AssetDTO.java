package com.adt.hrms.request;

import java.util.List;

import com.adt.hrms.model.AssetAttributeMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO {

	private Integer assetId;
	private Integer assetTypeId;

	private List<AssetAttributeMapping> assetAttributeMappingList;

}
