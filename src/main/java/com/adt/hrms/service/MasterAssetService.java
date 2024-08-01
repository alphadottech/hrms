package com.adt.hrms.service;

import com.adt.hrms.model.AssetEmployeeMapping;
import com.adt.hrms.model.AssetType;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.ResponseDTO;

public interface MasterAssetService {

	public ResponseDTO getAllAssetType();

	public ResponseDTO getAllAssetAttributesByAssetTypeId(Integer assetTypeId);

	public ResponseDTO saveAssetInfo(AssetDTO assetDTO);

	public ResponseDTO deleteAssetInfoById(Integer assetId);

	public ResponseDTO getAllAssetInfoByAssetTypeIdAndPagination(Integer assetTypeId, int page, int size);

	public ResponseDTO getAllAssignedAssetsToEmpById(String empId);

	public ResponseDTO getAssetInfoById(Integer assetId);

	public ResponseDTO assignAssetToEmp(AssetEmployeeMapping assetEmployeeMapping);

	public ResponseDTO updateAssetAttributeMappingByAssetId(AssetDTO assetDTO);

	public ResponseDTO addAssetType(AssetType assetType);

	public ResponseDTO getAssetTypeById(Integer assetTypeId);

	public ResponseDTO updateAssetTypeById(Integer assetTypeId, AssetType assetType);

	public ResponseDTO deleteAssetTypeById(Integer assetTypeId);

	public ResponseDTO addAssetAttributesByAssetTypeId(Integer assetTypeId, String assetAttributeName);

	public ResponseDTO updateAssetAttributeById(Integer assetAttributeId, Integer assetTypeId,
			String assetAttributeName);

	public ResponseDTO deleteAssetAttributeById(Integer assetAttributeId);

//	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO);
//	public boolean saveMasterAsset(MasterAsset asset);
//	public MasterAsset TakeAssetById(Integer id);
//	public List<MasterAsset> SearchByAssetUser(String assetUser);
//	public List<MasterAsset> SearchByStatus(String status);
//	public List<MasterAsset> SearchByAssetType(String assetType);
//	public String updateMasterAssetById(MasterAsset masterAsset);
//	public List<MasterAsset> findAllMasterAsset();

}
