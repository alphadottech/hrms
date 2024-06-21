package com.adt.hrms.request;

import java.util.List;

import com.adt.hrms.model.AssetAttribute;
import com.adt.hrms.model.AssetType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssetDTO {

	private String assetName;

	private List<AssetAttribute> assetAttributeList;

}
