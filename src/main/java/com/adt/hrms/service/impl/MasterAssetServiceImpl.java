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
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId,AssetTypeId should not be 0 or Invalid or Null");
			}

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
		} catch (IllegalArgumentException e) {
			log.error("getAllAssetAttributesByAssetTypeId IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
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

			if (createAssetDTO == null || createAssetDTO.equals("")) {
				throw new IllegalArgumentException("CreateAssetDTO should not be null or Invalid");
			}
			if (createAssetDTO.getAssetName() == null || createAssetDTO.getAssetName().equals("")) {
				throw new IllegalArgumentException("Asset Name should not be null or Invalid");
			}
			if (createAssetDTO.getAssetAttributeList() == null || createAssetDTO.getAssetAttributeList().equals("")) {
				throw new IllegalArgumentException("Asset Attribute list should not be null or Invalid");
			}
			for (AssetAttribute assetAttribute : createAssetDTO.getAssetAttributeList()) {
				if (assetAttribute.getName() == null || assetAttribute.getName().equals("")) {
					throw new IllegalArgumentException("Asset Attribute name should not be null or Invalid");
				}
			}

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

		} catch (IllegalArgumentException e) {
			log.error("saveAssetDetailsWithAttributes IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
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

			if (assetDTO == null || assetDTO.equals("")) {
				throw new IllegalArgumentException("AssetDTO should not be Null or Invalid");
			}
			if (assetDTO.getAssetTypeId() == 0) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Null or Invalid");
			}
			if (assetDTO.getAssetAttributeId() == 0) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Null or Invalid");
			}
			if (assetDTO.getAssetAttributeValue() == null || assetDTO.getAssetAttributeValue().equals("")) {
				throw new IllegalArgumentException("AssetAttributeValue should not be Null or Invalid");
			}

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

		} catch (IllegalArgumentException e) {
			log.error("saveAssetInfo IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("saveAssetInfo Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAssetByAssetTypeId(Integer assetTypeId, Integer assetAttributeId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:deleteAssetByAssetTypeId info level log message");
		try {

			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}
			if (assetAttributeId == 0 || assetAttributeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId,AssetAttributeId should not be 0 or Invalid or Null");
			}

			Optional<List<AssetInfo>> assetInfoListExist = assetInfoRepo.findAssetInfoListByAssetTypeId(assetTypeId);

			if (assetInfoListExist.isPresent() && !assetInfoListExist.get().isEmpty()) {
				responseDTO.setMessage(
						"This AssetInfo is already associated with AssetTypeId: " + assetTypeId + ", we cannot delete");
				responseDTO.setStatus("AlreadyAssociated");
				responseDTO.setData(null);
				return responseDTO;
			}

			Optional<List<AssetAttribute>> assetAttributeListExist = assetAttributeRepo
					.findAssetAttributeByAssetTypeId(assetAttributeId, assetTypeId);

			if (assetAttributeListExist.isPresent() && !assetAttributeListExist.get().isEmpty()) {
				responseDTO.setMessage("This AssetAttribute is already associated with AssetTypeId: " + assetTypeId
						+ ", we cannot delete");
				responseDTO.setStatus("AlreadyAssociated");
				responseDTO.setData(null);
				return responseDTO;
			}

			Optional<AssetType> assetTypeExist = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);

			if (assetTypeExist.isPresent()) {

				if (!assetInfoListExist.isPresent() && assetInfoListExist.get().isEmpty()) {
					assetInfoRepo.deleteByAssetTypeId(assetTypeId);
				}

				if (!assetAttributeListExist.isPresent() && assetAttributeListExist.get().isEmpty()) {
					assetAttributeRepo.deleteByAssetTypeId(assetTypeId);
				}

				assetTypeRepo.delete(assetTypeExist.get());

				responseDTO.setMessage("AssetType with AssetTypeId: " + assetTypeId + " is deleted successfully");
				responseDTO.setStatus("Success");
				responseDTO.setData(null);
			} else {
				responseDTO.setMessage("AssetType with AssetTypeId: " + assetTypeId + " is not found");
				responseDTO.setStatus("NotFound");
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("deleteAssetByAssetTypeId IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetByAssetTypeId Exception: " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("Failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO updateAssetAttributeValueByAssetId(Integer assetId, Integer assetAttributeId,
			String assetAttributeValue) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:updateAssetAttributeValueByAssetId info level log message");
		try {

			if (assetId == 0) {
				throw new IllegalArgumentException(
						"Provide Valid AssetId, AssetTypeId should not be 0 or Invalid or Null");
			}
			if (assetAttributeId == 0) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Invalid or Null");
			}
			if (assetAttributeValue == null || assetAttributeValue.equals("")) {
				throw new IllegalArgumentException("AssetAttributeValue should not be null or Invalid");
			}

			Optional<AssetInfo> assetInfoExist = assetInfoRepo.findAssetByAssetId(assetId);
			if (assetInfoExist.isPresent()) {
				Optional<AssetAttributeMapping> assetAttributeMappingExist = assetAttributeMappingRepo
						.findByAssetIdAndAssetAttributeId(assetId, assetAttributeId);

				if (assetAttributeMappingExist.isPresent()) {

					assetAttributeMappingExist.get().setAssetAttributeValue(assetAttributeValue);
					assetAttributeMappingRepo.save(assetAttributeMappingExist.get());

					responseDTO.setMessage("AssetAttributeValue with AssetId:" + assetId + " and AssetAttributeId:"
							+ assetAttributeId + " is Updated Successfully");
					responseDTO.setStatus("success");
					responseDTO.setData(null);
				} else {
					responseDTO.setMessage("AssetAttributeValue is not Updated since it's Not Exist with AssetId:"
							+ assetId + " and AssetAttributeId:" + assetAttributeId);
					responseDTO.setStatus("NotSaved");
					responseDTO.setData(null);
					return responseDTO;
				}
			} else {
				responseDTO.setMessage("AssetInfo with AssetId:" + assetId + " is not found");
				responseDTO.setStatus("NotFound");
				responseDTO.setData(null);
				return responseDTO;
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetAttributeValueByAssetId IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeValueByAssetId Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

}
