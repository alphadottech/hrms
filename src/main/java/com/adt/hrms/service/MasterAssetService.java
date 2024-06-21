package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.CreateAssetDTO;
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

	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO);

	public ResponseDTO saveAssetInfo(AssetDTO assetDTO);

}
