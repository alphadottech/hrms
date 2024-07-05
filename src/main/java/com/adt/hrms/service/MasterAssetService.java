package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.AssetType;
import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.ResponseDTO;

public interface MasterAssetService {

	public boolean saveMasterAsset(MasterAsset asset);

	public MasterAsset TakeAssetById(Integer id);

	public List<MasterAsset> SearchByAssetUser(String assetUser);

	public List<MasterAsset> SearchByStatus(String status);

	public List<MasterAsset> SearchByAssetType(String assetType);

	public String updateMasterAssetById(MasterAsset masterAsset);

	public List<MasterAsset> findAllMasterAsset();

	public ResponseDTO getAllAssetType();

	public ResponseDTO getAllAssetAttributesByAssetTypeId(Integer assetTypeId);

	public ResponseDTO saveAssetInfo(AssetDTO assetDTO);

	public ResponseDTO deleteAssetInfoById(Integer assetId);

	public ResponseDTO updateAssetAttributeMappingByAssetId(AssetDTO assetDTO);

	public ResponseDTO addAssetType(AssetType assetType);

	public ResponseDTO getAssetTypeById(Integer assetTypeId);

	public ResponseDTO updateAssetTypeById(Integer assetTypeId, String assetTypeName);

	public ResponseDTO deleteAssetTypeById(Integer assetTypeId);

	public ResponseDTO addAssetAttributesByAssetTypeId(Integer assetTypeId, String assetAttributeName);

	public ResponseDTO updateAssetAttributeById(Integer assetAttributeId, Integer assetTypeId,
			String assetAttributeName);

	public ResponseDTO deleteAssetAttributeById(Integer assetAttributeId);

//	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO);

}
