package com.adt.hrms.service;

import com.adt.hrms.model.AssetType;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.AssignAssetsDTO;
import com.adt.hrms.request.ResponseDTO;

public interface MasterAssetService {

	public ResponseDTO getAllAssetType();

	public ResponseDTO getAllAssetAttributesByAssetTypeId(Integer assetTypeId);

	public ResponseDTO saveAssetInfo(AssetDTO assetDTO);

	public ResponseDTO deleteAssetInfoById(Integer assetId);

	public ResponseDTO getAllAssetInfoByAssetTypeIdAndPagination(Integer assetTypeId, int page, int size);

	public ResponseDTO updateAssetAttributeMappingByAssetId(AssetDTO assetDTO);

	public ResponseDTO addAssetType(AssetType assetType);

	public ResponseDTO getAssetTypeById(Integer assetTypeId);

	public ResponseDTO updateAssetTypeById(Integer assetTypeId, AssetType assetType);

	public ResponseDTO deleteAssetTypeById(Integer assetTypeId);

	public ResponseDTO addAssetAttributesByAssetTypeId(Integer assetTypeId, String assetAttributeName);

	public ResponseDTO updateAssetAttributeById(Integer assetAttributeId, Integer assetTypeId,
			String assetAttributeName);

	public ResponseDTO deleteAssetAttributeById(Integer assetAttributeId);

	public ResponseDTO searchEmployeeDetails(String firstName, String lastName, String empAdtId, String firstLetter,
			int page, int size);

	public ResponseDTO getAllAssignedAssetsByEmpId(Integer empId);

	public ResponseDTO assignAllAssetsToEmp(AssignAssetsDTO assignAssetsDTO);

	public ResponseDTO getAssetInfoByAssetAdtId(String assetAdtId);

}
