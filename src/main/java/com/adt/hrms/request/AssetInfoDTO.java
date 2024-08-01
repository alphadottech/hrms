package com.adt.hrms.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetInfoDTO {

	private String empId;
	private String assetADTId; // asset_id 
	private String assetType;
	private String assetAbbreviation;
	private String assetStatus;

	List<AssetAttributeMappingDTO> assetAttributeMappingList;

}
