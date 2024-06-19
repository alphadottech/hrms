package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Optional;

import com.adt.hrms.util.AssetUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.repository.MasterAssetRepository;
import com.adt.hrms.service.MasterAssetService;

//JyotiPancholi - Jira no ->  HRMS-63(START)

@Service
public class MasterAssetServiceImpl implements MasterAssetService{

	@Autowired
	private MasterAssetRepository repo;
	
	@Override
	public boolean saveMasterAsset(MasterAsset asset) {
		if(!AssetUtility.checkValidate(asset.getAssetUser())){
			throw new IllegalArgumentException("Invalid Asset User...");
		}
		if(!AssetUtility.validateName(asset.getAssetName())){
			throw new IllegalArgumentException("Invalid Asset Name..");
		}
		if(!AssetUtility.validateId(asset.getAssetId())){
			throw new IllegalArgumentException("Invalid Asset ID ");
		}
		if(!AssetUtility.validateId(asset.getAssetNo())){
			throw new IllegalArgumentException("Invalid Asset Number");
		}
		if(!AssetUtility.checkValidate(asset.getAssetType())){
			throw new IllegalArgumentException("Invalid Asset Type...");
		}
		if(!AssetUtility.validateProcessor(asset.getProcessor())){
			throw new IllegalArgumentException("Invalid Processor Details");
		}
		if(!AssetUtility.validateRAM(asset.getRam())){
			throw new IllegalArgumentException("Invalid RAM Details");
		}
		if(!AssetUtility.validateDiskType(asset.getDiskType())){
			throw new IllegalArgumentException("Invalid Disc Type Details");
		}
		if(!AssetUtility.validateProcessor(asset.getOperatingSystem())){
			throw new IllegalArgumentException("Invalid Operating System Details");
		}
		if(!AssetUtility.validateProcessor(asset.getWarrenty())){
			throw new IllegalArgumentException("Invalid Warranty Details");
		}
		MasterAsset masterasset = repo.save(asset);
		
		return masterasset!=null;
	}
	
	@Override
	public MasterAsset TakeAssetById(Integer id) {
		 Optional<MasterAsset> getById = repo.findById(id);
		return getById.get();
	}
	
	//JyotiPancholi - Jira no ->  HRMS-83(START)
	@Override
	public List<MasterAsset> SearchByAssetUser(String assetUser) {
		return repo.findByAssetUser(assetUser);
	}

	@Override
	public List<MasterAsset> SearchByStatus(String status) {
		return repo.findByStatus(status);
	}

	@Override
	public List<MasterAsset> SearchByAssetType(String assetType) {
		return repo.findByAssetType(assetType);
	}
	//JyotiPancholi - Jira no ->  HRMS-83(END)

	//RitikaBhawsar - Jira no ->  HRMS-63(START)
	@Override
	public String updateMasterAssetById(MasterAsset masterAsset) 
	{
		if(!AssetUtility.checkValidate(masterAsset.getAssetUser())){
			throw new IllegalArgumentException("Invalid Asset User...");
		}
		if(!AssetUtility.validateName(masterAsset.getAssetName())){
			throw new IllegalArgumentException("Invalid Asset Name..");
		}
		if(!AssetUtility.validateId(masterAsset.getAssetId())){
			throw new IllegalArgumentException("Invalid Asset ID ");
		}
		if(!AssetUtility.validateId(masterAsset.getAssetNo())){
			throw new IllegalArgumentException("Invalid Asset Number");
		}
		if(!AssetUtility.checkValidate(masterAsset.getAssetType())){
			throw new IllegalArgumentException("Invalid Asset Type...");
		}
		if(!AssetUtility.validateProcessor(masterAsset.getProcessor())){
			throw new IllegalArgumentException("Invalid Processor Details");
		}
		if(!AssetUtility.validateRAM(masterAsset.getRam())){
			throw new IllegalArgumentException("Invalid RAM Details");
		}
		if(!AssetUtility.validateDiskType(masterAsset.getDiskType())){
			throw new IllegalArgumentException("Invalid Disc Type Details");
		}
		if(!AssetUtility.validateProcessor(masterAsset.getOperatingSystem())){
			throw new IllegalArgumentException("Invalid Operating System Details");
		}
		if(!AssetUtility.validateProcessor(masterAsset.getWarrenty())){
			throw new IllegalArgumentException("Invalid Warranty Details");
		}
		MasterAsset dbAsset = repo.findAssetById(masterAsset.getId());
		if(dbAsset!=null) 
		{
			dbAsset.setAssetName(masterAsset.getAssetName());
			dbAsset.setAssetNo(masterAsset.getAssetNo());
			dbAsset.setAssetType(masterAsset.getAssetType());
			dbAsset.setAssetUser(masterAsset.getAssetUser());
			dbAsset.setDiskType(masterAsset.getDiskType());
			dbAsset.setOperatingSystem(masterAsset.getOperatingSystem());
			dbAsset.setProcessor(masterAsset.getProcessor());
			dbAsset.setRam(masterAsset.getRam());
			dbAsset.setStatus(masterAsset.getStatus());
			dbAsset.setWarrenty(masterAsset.getWarrenty());
			dbAsset.setPurchesDate(masterAsset.getPurchesDate());
			dbAsset.setWarrentyDate(masterAsset.getWarrentyDate());
			
			return repo.save(dbAsset).getAssetId()+" Updated Successfully";
		}
		
		
		return masterAsset.getAssetId()+" Not Updated ";
	}

	@Override
	public List<MasterAsset> findAllMasterAsset()
	{
	return (List<MasterAsset>) repo.findAll();
	}
	//RitikaBhawsar - Jira no ->  HRMS-63(END)

}

//JyotiPancholi - Jira no ->  HRMS-63(END)
