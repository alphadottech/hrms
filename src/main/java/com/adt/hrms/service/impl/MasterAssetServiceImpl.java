package com.adt.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.AssetAttribute;
import com.adt.hrms.model.AssetAttributeMapping;
import com.adt.hrms.model.AssetInfo;
import com.adt.hrms.model.AssetType;
import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.repository.AssetAttributeMappingRepo;
import com.adt.hrms.repository.AssetAttributeRepo;
import com.adt.hrms.repository.AssetInfoRepo;
import com.adt.hrms.repository.AssetTypeRepo;
import com.adt.hrms.repository.MasterAssetRepository;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.CreateAssetDTO;
import com.adt.hrms.request.ResponseDTO;
import com.adt.hrms.service.MasterAssetService;
import com.adt.hrms.util.AssetUtility;

@Service
public class MasterAssetServiceImpl implements MasterAssetService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AssetTypeRepo assetTypeRepo;

	@Autowired
	private AssetInfoRepo assetInfoRepo;

	@Autowired
	private AssetAttributeRepo assetAttributeRepo;

	@Autowired
	private AssetAttributeMappingRepo assetAttributeMappingRepo;

	@Autowired
	private MasterAssetRepository repo;

	@Override
	public boolean saveMasterAsset(MasterAsset asset) {
		if (!AssetUtility.checkValidate(asset.getAssetUser())) {
			throw new IllegalArgumentException("Invalid Asset User...");
		}
		if (!AssetUtility.validateName(asset.getAssetName())) {
			throw new IllegalArgumentException("Invalid Asset Name..");
		}
		if (!AssetUtility.validateId(asset.getAssetId())) {
			throw new IllegalArgumentException("Invalid Asset ID ");
		}
		if (!AssetUtility.validateId(asset.getAssetNo())) {
			throw new IllegalArgumentException("Invalid Asset Number");
		}
		if (!AssetUtility.checkValidate(asset.getAssetType())) {
			throw new IllegalArgumentException("Invalid Asset Type...");
		}
		if (!AssetUtility.validateProcessor(asset.getProcessor())) {
			throw new IllegalArgumentException("Invalid Processor Details");
		}
		if (!AssetUtility.validateRAM(asset.getRam())) {
			throw new IllegalArgumentException("Invalid RAM Details");
		}
		if (!AssetUtility.validateDiskType(asset.getDiskType())) {
			throw new IllegalArgumentException("Invalid Disc Type Details");
		}
		if (!AssetUtility.validateProcessor(asset.getOperatingSystem())) {
			throw new IllegalArgumentException("Invalid Operating System Details");
		}
		if (!AssetUtility.validateProcessor(asset.getWarrenty())) {
			throw new IllegalArgumentException("Invalid Warranty Details");
		}
		MasterAsset masterasset = repo.save(asset);

		return masterasset != null;
	}

	@Override
	public MasterAsset TakeAssetById(Integer id) {
		Optional<MasterAsset> getById = repo.findById(id);
		return getById.get();
	}

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

	@Override
	public String updateMasterAssetById(MasterAsset masterAsset) {
		if (!AssetUtility.checkValidate(masterAsset.getAssetUser())) {
			throw new IllegalArgumentException("Invalid Asset User...");
		}
		if (!AssetUtility.validateName(masterAsset.getAssetName())) {
			throw new IllegalArgumentException("Invalid Asset Name..");
		}
		if (!AssetUtility.validateId(masterAsset.getAssetId())) {
			throw new IllegalArgumentException("Invalid Asset ID ");
		}
		if (!AssetUtility.validateId(masterAsset.getAssetNo())) {
			throw new IllegalArgumentException("Invalid Asset Number");
		}
		if (!AssetUtility.checkValidate(masterAsset.getAssetType())) {
			throw new IllegalArgumentException("Invalid Asset Type...");
		}
		if (!AssetUtility.validateProcessor(masterAsset.getProcessor())) {
			throw new IllegalArgumentException("Invalid Processor Details");
		}
		if (!AssetUtility.validateRAM(masterAsset.getRam())) {
			throw new IllegalArgumentException("Invalid RAM Details");
		}
		if (!AssetUtility.validateDiskType(masterAsset.getDiskType())) {
			throw new IllegalArgumentException("Invalid Disc Type Details");
		}
		if (!AssetUtility.validateProcessor(masterAsset.getOperatingSystem())) {
			throw new IllegalArgumentException("Invalid Operating System Details");
		}
		if (!AssetUtility.validateProcessor(masterAsset.getWarrenty())) {
			throw new IllegalArgumentException("Invalid Warranty Details");
		}
		MasterAsset dbAsset = repo.findAssetById(masterAsset.getId());
		if (dbAsset != null) {
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

			return repo.save(dbAsset).getAssetId() + " Updated Successfully";
		}

		return masterAsset.getAssetId() + " Not Updated ";
	}

	@Override
	public List<MasterAsset> findAllMasterAsset() {
		return (List<MasterAsset>) repo.findAll();
	}

	@Override
	public ResponseDTO getAllAssetType() {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:masterAsset:getAllAssetType info level log message");
		try {
			List<AssetType> assetTypesList = assetTypeRepo.findAll();

			if (assetTypesList.isEmpty()) {

				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No AssetType found");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("Success");
				responseDTO.setMessage("Asset types Fetched successfully");
				responseDTO.setData(assetTypesList);
			}
		} catch (Exception e) {
			log.error("getAllAssetType Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO getAllAssetAttributesByAssetTypeId(Integer assetTypeId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:masterAsset:getAllAssetAttributesByAssetTypeId info level log message");
		try {
			List<AssetAttribute> assetAttributeList = assetAttributeRepo
					.findAllAssetAttributesByAssetTypeId(assetTypeId);

			if (assetAttributeList.isEmpty()) {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No Asset Attributes found");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("Success");
				responseDTO.setMessage("Asset Attributes with AssetTypeId: " + assetTypeId + " Fetched successfully");
				responseDTO.setData(assetAttributeList);
			}
		} catch (Exception e) {
			log.error("getAllAssetAttributesByAssetTypeId Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:masterAsset:saveAssetDetailsWithAttributes info level log message");
		try {
			Optional<AssetType> assetTypeExist = assetTypeRepo.findByAssetName(createAssetDTO.getAssetName());

			AssetType assetType;
			AssetType assetTypeSaved = null;
			AssetAttribute assetAttributeSaved = null;

			if (assetTypeExist.isPresent()) {
				assetTypeSaved = assetTypeExist.get();
			} else {
				assetType = new AssetType();
				assetType.setAssetName(createAssetDTO.getAssetName());
				assetTypeSaved = assetTypeRepo.save(assetType);
			}
			List<AssetAttribute> attributesFinalList = new ArrayList<>();
			for (AssetAttribute assetAttri : createAssetDTO.getAssetAttributeList()) {

				Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo
						.findByAssetAttributeName(assetAttri.getName(), assetTypeSaved.getId());

				if (assetAttributeExist.isPresent()) {
					assetAttributeSaved = assetAttributeExist.get();
				} else {
					assetAttributeSaved = new AssetAttribute();
					assetAttributeSaved.setName(assetAttri.getName());
					assetAttributeSaved.setAsset_type_id(assetTypeSaved.getId());
					assetAttributeSaved.setAssetType(assetTypeSaved);
				}
				AssetAttribute savedAttribute = assetAttributeRepo.save(assetAttributeSaved);
				attributesFinalList.add(savedAttribute);
			}
			createAssetDTO.setAssetAttributeList(attributesFinalList);

			responseDTO
					.setMessage(assetTypeExist.isPresent() ? "Data Updated Successfully" : "Data Saved Successfully");
			responseDTO.setStatus("success");
			responseDTO.setData(createAssetDTO);

		} catch (Exception e) {
			log.error("saveAssetDetailsWithAttributes Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO saveAssetInfo(AssetDTO assetDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:saveAssetInfo info level log message");

		try {
			AssetInfo asset = new AssetInfo();
			asset.setAsset_type_id(assetDTO.getAssetTypeId());
			AssetInfo savedAssetInfo = assetInfoRepo.save(asset);

			if (savedAssetInfo != null && !savedAssetInfo.equals("")) {
				AssetAttributeMapping assetAttributeMapping = new AssetAttributeMapping();

				assetAttributeMapping.setAsset_id(savedAssetInfo.getId());
				assetAttributeMapping.setAsset_attribute_id(assetDTO.getAssetAttributeId());
				assetAttributeMapping.setAssetAttributeValue(assetDTO.getAssetAttributeValue());
				assetAttributeMappingRepo.save(assetAttributeMapping);

			} else {
				responseDTO.setStatus("NotSaved");
				responseDTO.setMessage("AssetInfo data not saved yet");
				responseDTO.setData(assetDTO);
			}
			responseDTO.setStatus("Success");
			responseDTO.setMessage("Data Saved Successfully");
			responseDTO.setData(assetDTO);

		} catch (Exception e) {
			log.error("saveAssetInfo Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

}
