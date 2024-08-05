package com.adt.hrms.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignAssetsDTO {

	private String empAdtId;
	private List<String> assetAdtIdList;

}
