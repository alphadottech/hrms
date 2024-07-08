package com.adt.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.adt.hrms.request.ResponseDTO;
import com.adt.hrms.service.MasterAssetService;
import com.adt.hrms.util.AssetUtility;

@Service
public class MasterAssetServiceImpl implements MasterAssetService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final int MAX_PAGE_SIZE = 50;
	private static final int DEFAULT_PAGE_SIZE = 10;

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
				responseDTO.setMessage("All AssetType Fetched successfully");
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
				responseDTO.setMessage("No AssetAttributes found");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("Success");
				responseDTO.setMessage("AssetAttributes with AssetTypeId: " + assetTypeId + " Fetched successfully");
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
	public ResponseDTO saveAssetInfo(AssetDTO assetDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:saveAssetInfo info level log message");
		try {
			if (assetDTO.getAssetTypeId() == 0 || assetDTO.getAssetTypeId().equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Null or Invalid");
			}
			if (assetDTO.getAssetAttributeMappingList() == null) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Null or Invalid");
			}
			for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {
				if (assetAttributeMapping.getAsset_attribute_id() == 0
						|| assetAttributeMapping.getAsset_attribute_id().equals("")) {
					throw new IllegalArgumentException(
							"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Null or Invalid");
				}
				if ((assetAttributeMapping.getAssetAttributeValue() == null
						|| assetAttributeMapping.getAssetAttributeValue().equals(""))
						|| assetAttributeMapping.getAssetAttributeValue().isEmpty()
						|| (assetAttributeMapping.getAssetAttributeValue().length() > 200)) {
					throw new IllegalArgumentException(
							"AssetAttributeValue should not be Null or Invalid, AND AssetAttributeValue should not be Greater than 200 Characters");
				}
			}

			AssetInfo asset = new AssetInfo();
			asset.setAsset_type_id(assetDTO.getAssetTypeId());
			AssetInfo savedAssetInfo = assetInfoRepo.save(asset);

			if (savedAssetInfo != null && !savedAssetInfo.equals("")) {

				for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {

					AssetAttributeMapping assetAttributeMappingSaved = new AssetAttributeMapping();

					assetAttributeMappingSaved.setAsset_id(savedAssetInfo.getId());
					assetAttributeMappingSaved.setAsset_attribute_id(assetAttributeMapping.getAsset_attribute_id());
					assetAttributeMappingSaved.setAssetAttributeValue(assetAttributeMapping.getAssetAttributeValue());
					assetAttributeMappingRepo.save(assetAttributeMappingSaved);
				}
				responseDTO.setStatus("Success");
				responseDTO.setMessage("AssetInfo Data Saved Successfully");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("NotSaved");
				responseDTO.setMessage("AssetInfo data not saved yet");
				responseDTO.setData(null);
				return responseDTO;
			}
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
	public ResponseDTO updateAssetAttributeMappingByAssetId(AssetDTO assetDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:updateAssetAttributeMappingByAssetId info level log message");

		try {
			if (assetDTO.getAssetId() == null || assetDTO.getAssetId() == 0 || assetDTO.getAssetId().equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetInfoId, AssetInfoId should not be 0 or Invalid or Null");
			}
			if (assetDTO.getAssetAttributeMappingList() == null) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Null or Invalid");
			}
			for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {
				if (assetAttributeMapping.getAsset_attribute_id() == 0
						|| assetAttributeMapping.getAsset_attribute_id().equals("")) {
					throw new IllegalArgumentException(
							"Provide Valid AssetAttributeId, AssetAttributeId should not be 0 or Null or Invalid");
				}
				if ((assetAttributeMapping.getAssetAttributeValue() == null
						|| assetAttributeMapping.getAssetAttributeValue().equals(""))
						|| assetAttributeMapping.getAssetAttributeValue().isEmpty()
						|| (assetAttributeMapping.getAssetAttributeValue().length() > 200)) {
					throw new IllegalArgumentException(
							"AssetAttributeValue should not be Null or Invalid, AND AssetAttributeValue should not be Greater than 200 Characters");
				}
			}

			Optional<AssetInfo> assetInfoExist = assetInfoRepo.findAssetByAssetId(assetDTO.getAssetId());

			if (assetInfoExist.isPresent()) {

				Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
						.findMappingListByAssetId(assetDTO.getAssetId());

				if (assetAttributeMappingListExist.isPresent()) {

					for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingListExist.get()) {

						assetAttributeMappingRepo.deleteById(assetAttributeMapping.getId());
					}

					for (AssetAttributeMapping attributeMapping : assetDTO.getAssetAttributeMappingList()) {
						AssetAttributeMapping saveAssetAttributeMapping = new AssetAttributeMapping();

						saveAssetAttributeMapping.setAsset_id(assetDTO.getAssetId());
						saveAssetAttributeMapping.setAsset_attribute_id(attributeMapping.getAsset_attribute_id());
						saveAssetAttributeMapping.setAssetAttributeValue(attributeMapping.getAssetAttributeValue());
						assetAttributeMappingRepo.save(saveAssetAttributeMapping);
					}
					responseDTO.setMessage(
							"AssetAttributeMapping is Updated Successfully with AssetId: " + assetDTO.getAssetId());
					responseDTO.setStatus("success");
					responseDTO.setData(null);
					return responseDTO;
				}
				responseDTO.setMessage("AssetInfo is Not Updated with AssetInfoId: " + assetDTO.getAssetId());
				responseDTO.setStatus("NotUpdated");
				responseDTO.setData(null);
				return responseDTO;

			} else {
				responseDTO.setMessage("AssetInfo is Not found with AssetInfoId: " + assetDTO.getAssetId());
				responseDTO.setStatus("NotFound");
				responseDTO.setData(null);
				return responseDTO;
			}

		} catch (IllegalArgumentException e) {
			log.error("updateAssetAttributeMappingByAssetId IllegalArgumentException: " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeMappingByAssetId Exception: " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAssetInfoById(Integer assetId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:deleteAssetInfoById info level log message");
		try {
			if (assetId == 0 || assetId.equals("")) {
				throw new IllegalArgumentException("Provide Valid AssetId, AssetId should not be 0 or Invalid or Null");
			}
			Optional<AssetInfo> assetInfoExist = assetInfoRepo.findAssetByAssetId(assetId);

			if (assetInfoExist.isPresent()) {

				Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
						.findMappingListByAssetId(assetId);

				if (assetAttributeMappingListExist.isPresent()) {

					for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingListExist.get()) {

						assetAttributeMappingRepo.deleteById(assetAttributeMapping.getId());
					}
				}

				assetInfoRepo.deleteById(assetId);

				responseDTO.setMessage("AssetInfo with ID:" + assetId + " Deleted Successfully");
				responseDTO.setStatus("success");
				responseDTO.setData(null);
			} else {
				responseDTO.setMessage("AssetInfo with ID:" + assetId + " not Found");
				responseDTO.setStatus("NotFound");
				responseDTO.setData(null);
				return responseDTO;
			}
		} catch (IllegalArgumentException e) {
			log.error("deleteAssetInfoById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetInfoById Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO getAllAssetInfoByAssetTypeIdAndPagination(Integer assetTypeId, int page, int size) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:getAllAssetInfoByAssetTypeIdAndPagination info level log message");
		try {

			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId,AssetTypeId should not be 0 or Invalid or Null");
			}

			if (size <= 0 || size > MAX_PAGE_SIZE) {
				size = DEFAULT_PAGE_SIZE;
			}

			Pageable pageable = PageRequest.of(page, size);
			Page<AssetInfo> assetInfoListPage = assetInfoRepo.findListByAssetTypeIdWithPagination(assetTypeId,
					pageable);

			if (!assetInfoListPage.isEmpty()) {

				List<AssetDTO> assetDTOList = new ArrayList<>();

				for (AssetInfo assetInfo : assetInfoListPage) {

					List<AssetAttributeMapping> assetAttributeMappingsList = assetAttributeMappingRepo
							.findAssetMappingListByAssetId(assetInfo.getId());

					AssetDTO assetDTO = new AssetDTO();

					assetDTO.setAssetId(assetInfo.getId());
					assetDTO.setAssetTypeId(assetInfo.getAsset_type_id());

					if (!assetAttributeMappingsList.isEmpty()) {
						assetDTO.setAssetAttributeMappingList(assetAttributeMappingsList);
					} else {
						assetDTO.setAssetAttributeMappingList(null);
					}
					assetDTOList.add(assetDTO);
				}

				responseDTO.setStatus("success");
				responseDTO.setMessage("AssetInfoList found for AssetTypeId: " + assetTypeId);
				responseDTO.setData(assetDTOList);
			} else {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("AssetInfoList not found for AssetTypeId: " + assetTypeId);
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("getAllAssetInfoByAssetTypeIdAndPagination IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAllAssetInfoByAssetTypeIdAndPagination Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO addAssetType(AssetType assetType) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:addAssetType info level log message");
		try {

			if (assetType == null || assetType.equals("")) {
				throw new IllegalArgumentException("AssetType should not be Null or Invalid");
			}
			if ((assetType.getAssetName() == null || assetType.getAssetName().equals(""))
					|| (assetType.getAssetName().length() > 200)) {
				throw new IllegalArgumentException(
						"AssetTypeName should not be Null or Invalid, AND AssetTypeName should not be Greater than 200 Characters ");
			}

			if (assetType != null && !assetType.equals("")) {
				assetTypeRepo.save(assetType);

				responseDTO.setStatus("Success");
				responseDTO.setMessage("Data Saved Successfully");
				responseDTO.setData(null);

			} else {
				responseDTO.setStatus("NotSaved");
				responseDTO.setMessage("AssetType data not saved yet");
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("addAssetType IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: addAssetType Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO getAssetTypeById(Integer assetTypeId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:getAssetTypeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}

			Optional<AssetType> assetType = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);

			if (assetType.isEmpty()) {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No AssetType found");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("Success");
				responseDTO.setMessage("AssetType with Id: " + assetTypeId + " Fetched successfully");
				responseDTO.setData(assetType.get());
			}
		} catch (IllegalArgumentException e) {
			log.error("getAssetTypeById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAssetTypeById Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO updateAssetTypeById(Integer assetTypeId, String assetTypeName) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:updateAssetTypeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}
			if ((assetTypeName == null || assetTypeName.equals("")) || (assetTypeName.length() > 200)) {
				throw new IllegalArgumentException(
						"AssetTypeName should not be Null or Invalid, AND AssetTypeName should not be Greater than 200 Characters ");
			}

			Optional<AssetType> assetType = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);

			if (assetType.isPresent()) {

				assetType.get().setAssetName(assetTypeName);
				assetTypeRepo.save(assetType.get());

				responseDTO.setStatus("Success");
				responseDTO.setMessage("AssetType with AssetTypeId: " + assetTypeId + " Updated successfully");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No AssetType found");
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetTypeById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetTypeById Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAssetTypeById(Integer assetTypeId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:deleteAssetTypeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}

			Optional<List<AssetInfo>> assetInfoListExist = assetInfoRepo.findAssetInfoListByAssetTypeId(assetTypeId);
			if (assetInfoListExist.isPresent() && !assetInfoListExist.get().isEmpty()) {
				for (AssetInfo assetInfo : assetInfoListExist.get()) {
					Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
							.findAssetAttributeMappingListByAssetInfoId(assetInfo.getId());
					if (assetAttributeMappingListExist.isPresent() && !assetAttributeMappingListExist.get().isEmpty()) {

						responseDTO.setMessage("This AssetAttributeMapping is already associated with AssetTypeId: "
								+ assetTypeId + ", we cannot delete");
						responseDTO.setStatus("AlreadyAssociated");
						responseDTO.setData(null);
						return responseDTO;
					}
				}
				responseDTO.setMessage(
						"This AssetInfo is already associated with AssetTypeId: " + assetTypeId + ", we cannot delete");
				responseDTO.setStatus("AlreadyAssociated");
				responseDTO.setData(null);
				return responseDTO;
			}

			Optional<List<AssetAttribute>> assetAttributeListExist = assetAttributeRepo
					.findAssetAttributeByAssetTypeId(assetTypeId);
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

				responseDTO.setMessage("AssetType with Id: " + assetTypeId + " is Deleted successfully");
				responseDTO.setStatus("Success");
				responseDTO.setData(null);
			} else {
				responseDTO.setMessage("AssetType with AssetTypeId: " + assetTypeId + " is not found");
				responseDTO.setStatus("NotFound");
				responseDTO.setData(null);
			}

		} catch (IllegalArgumentException e) {
			log.error("deleteAssetTypeById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetTypeById Exception: " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("Failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO addAssetAttributesByAssetTypeId(Integer assetTypeId, String assetAttributeName) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:addAssetAttributesByAssetTypeId info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}
			if ((assetAttributeName == null || assetAttributeName.equals("")) || (assetAttributeName.length() > 200)) {
				throw new IllegalArgumentException(
						"AssetAttributeName should not be Null or Invalid, AND AssetAttributeName should not be Greater than 200 Characters ");
			}

			if (assetAttributeName != null && !assetAttributeName.equals("")) {

				AssetAttribute assetAttributeSave = new AssetAttribute();
				assetAttributeSave.setAssetAttributeName(assetAttributeName);
				assetAttributeSave.setAsset_type_id(assetTypeId);

				assetAttributeRepo.save(assetAttributeSave);

				responseDTO.setStatus("Success");
				responseDTO.setMessage("Data Saved Successfully");
				responseDTO.setData(null);

			} else {
				responseDTO.setStatus("NotSaved");
				responseDTO.setMessage("AssetType data not saved yet");
				responseDTO.setData(null);
			}

		} catch (IllegalArgumentException e) {
			log.error("addAssetAttributesByAssetTypeId IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: addAssetAttributesByAssetTypeId Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO updateAssetAttributeById(Integer assetAttributeId, Integer assetTypeId,
			String assetAttributeName) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:updateAssetAttributeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetTypeId, AssetTypeId should not be 0 or Invalid or Null");
			}
			if (assetAttributeId == 0 || assetAttributeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId,AssetAttributeId should not be 0 or Invalid or Null");
			}
			if ((assetAttributeName == null || assetAttributeName.equals("")) || (assetAttributeName.length() > 200)) {
				throw new IllegalArgumentException(
						"AssetAttributeName should not be Null or Invalid, AND AssetAttributeName should not be Greater than 200 Characters ");
			}

			Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo.findById(assetAttributeId);

			if (assetAttributeExist.isPresent()) {

				assetAttributeExist.get().setAssetAttributeName(assetAttributeName);
				assetAttributeExist.get().getAssetType();
				assetAttributeExist.get().setAsset_type_id(assetTypeId);
				assetAttributeRepo.save(assetAttributeExist.get());

				responseDTO.setStatus("Success");
				responseDTO.setMessage(
						"AssetAttribute with AssetAttributeId: " + assetAttributeId + " Updated successfully");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No AssetAttribute found");
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetAttributeById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeById Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAssetAttributeById(Integer assetAttributeId) {
		ResponseDTO responseDTO = new ResponseDTO();
		log.info("MasterAssetServiceImpl:deleteAssetAttributeById info level log message");
		try {
			if (assetAttributeId == 0 || assetAttributeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide Valid AssetAttributeId,AssetAttributeId should not be 0 or Invalid or Null");
			}

			Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo.findById(assetAttributeId);

			if (assetAttributeExist.isPresent()) {

				assetAttributeRepo.deleteById(assetAttributeId);

				responseDTO.setStatus("Success");
				responseDTO.setMessage("AssetAttribute with Id: " + assetAttributeId + " Deleted successfully");
				responseDTO.setData(null);
			} else {
				responseDTO.setStatus("NotFound");
				responseDTO.setMessage("No AssetAttribute found");
				responseDTO.setData(null);
			}
		} catch (IllegalArgumentException e) {
			log.error("deleteAssetAttributeById IllegalArgumentException : " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetAttributeById Exception : " + e);
			e.printStackTrace();
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus("failed");
			responseDTO.setData(null);
		}
		return responseDTO;
	}

//	@Override
//	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO) {
//		ResponseDTO responseDTO = new ResponseDTO();
//		log.info("MasterAssetServiceImpl:masterAsset:saveAssetDetailsWithAttributes info level log message");
//		try {
//
//			if (createAssetDTO == null || createAssetDTO.equals("")) {
//				throw new IllegalArgumentException("CreateAssetDTO should not be null or Invalid");
//			}
//			if (createAssetDTO.getAssetName() == null || createAssetDTO.getAssetName().equals("")) {
//				throw new IllegalArgumentException("Asset Name should not be null or Invalid");
//			}
//			if (createAssetDTO.getAssetAttributeList() == null || createAssetDTO.getAssetAttributeList().equals("")) {
//				throw new IllegalArgumentException("Asset Attribute list should not be null or Invalid");
//			}
//			for (AssetAttribute assetAttribute : createAssetDTO.getAssetAttributeList()) {
//				if (assetAttribute.getAssetAttributeName() == null
//						|| assetAttribute.getAssetAttributeName().equals("")) {
//					throw new IllegalArgumentException("Asset Attribute name should not be null or Invalid");
//				}
//			}
//
//			Optional<AssetType> assetTypeExist = assetTypeRepo.findByAssetName(createAssetDTO.getAssetName());
//
//			AssetType assetType;
//			AssetType assetTypeSaved = null;
//			AssetAttribute assetAttributeSaved = null;
//
//			if (assetTypeExist.isPresent()) {
//				assetTypeSaved = assetTypeExist.get();
//			} else {
//				assetType = new AssetType();
//				assetType.setAssetName(createAssetDTO.getAssetName());
//				assetTypeSaved = assetTypeRepo.save(assetType);
//			}
//			List<AssetAttribute> attributesFinalList = new ArrayList<>();
//			for (AssetAttribute assetAttri : createAssetDTO.getAssetAttributeList()) {
//
//				Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo
//						.findByAssetAttributeName(assetAttri.getAssetAttributeName(), assetTypeSaved.getId());
//
//				if (assetAttributeExist.isPresent()) {
//					assetAttributeSaved = assetAttributeExist.get();
//				} else {
//					assetAttributeSaved = new AssetAttribute();
//					assetAttributeSaved.setAssetAttributeName(assetAttri.getAssetAttributeName());
//					assetAttributeSaved.setAsset_type_id(assetTypeSaved.getId());
//					assetAttributeSaved.setAssetType(assetTypeSaved);
//				}
//				AssetAttribute savedAttribute = assetAttributeRepo.save(assetAttributeSaved);
//				attributesFinalList.add(savedAttribute);
//			}
//			createAssetDTO.setAssetAttributeList(attributesFinalList);
//
//			responseDTO
//					.setMessage(assetTypeExist.isPresent() ? "Data Updated Successfully" : "Data Saved Successfully");
//			responseDTO.setStatus("success");
//			responseDTO.setData(createAssetDTO);
//
//		} catch (IllegalArgumentException e) {
//			log.error("saveAssetDetailsWithAttributes IllegalArgumentException : " + e.getMessage());
//			responseDTO.setMessage(e.getMessage());
//			responseDTO.setStatus("failed");
//			responseDTO.setData(null);
//		} catch (Exception e) {
//			log.error("saveAssetDetailsWithAttributes Exception : " + e);
//			e.printStackTrace();
//			responseDTO.setMessage(e.getMessage());
//			responseDTO.setStatus("failed");
//			responseDTO.setData(null);
//		}
//		return responseDTO;
//	}

}
