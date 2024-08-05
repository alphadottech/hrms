package com.adt.hrms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adt.hrms.model.AssetAttribute;
import com.adt.hrms.model.AssetAttributeMapping;
import com.adt.hrms.model.AssetInfo;
import com.adt.hrms.model.AssetType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.repository.AssetAttributeMappingRepo;
import com.adt.hrms.repository.AssetAttributeRepo;
import com.adt.hrms.repository.AssetInfoRepo;
import com.adt.hrms.repository.AssetTypeRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.request.AssetAttributeMappingDTO;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.AssetInfoDTO;
import com.adt.hrms.request.AssignAssetsDTO;
import com.adt.hrms.request.EmployeeDTO;
import com.adt.hrms.request.ResponseDTO;
import com.adt.hrms.service.MasterAssetService;
import com.adt.hrms.util.TableDataExtractor;

@Service
public class MasterAssetServiceImpl implements MasterAssetService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final int MAX_PAGE_SIZE = 50;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@Autowired
	private TableDataExtractor dataExtractor;

	@Autowired
	private AssetTypeRepo assetTypeRepo;

	@Autowired
	private AssetInfoRepo assetInfoRepo;

	@Autowired
	private AssetAttributeRepo assetAttributeRepo;

	@Autowired
	private AssetAttributeMappingRepo assetAttributeMappingRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	private ResponseDTO buildResponse(String status, String message, Object data) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(status);
		responseDTO.setMessage(message);
		responseDTO.setData(data);
		return responseDTO;
	}

	@Transactional
	public synchronized String generateAssetADTId(String assetName) {
		Optional<String> abbreviationOpt = assetTypeRepo.findAbbreviationByAssetName(assetName.toUpperCase());

		if (!abbreviationOpt.isPresent()) {
			throw new IllegalArgumentException("No abbreviation found for given asset name");
		}

		String abbreviation = abbreviationOpt.get().toUpperCase();
		String prefix = "ADT_" + abbreviation + "_";
		String sql = "SELECT asset_adt_id FROM employee_schema.asset WHERE asset_adt_id LIKE '" + prefix
				+ "%' ORDER BY asset_adt_id DESC LIMIT 1";
		List<Map<String, Object>> adtIdData = dataExtractor.extractDataFromTable(sql);

		int newIdNumber = 1;
		if (!adtIdData.isEmpty()) {
			String lastId = (String) adtIdData.get(0).get("asset_adt_id");
			String numberPart = lastId.substring(prefix.length());
			newIdNumber = Integer.parseInt(numberPart) + 1;
		}

		return String.format("%s%04d", prefix, newIdNumber);
	}

	@Override
	public ResponseDTO getAllAssetType() {
		log.info("MasterAssetServiceImpl:masterAsset:getAllAssetType info level log message");
		try {
			List<AssetType> assetTypesList = assetTypeRepo.findAll();

			if (assetTypesList.isEmpty()) {
				return buildResponse("NotFound", "AssetType not found", null);
			} else {
				return buildResponse("Success", "All asset type fetched successfully", assetTypesList);
			}
		} catch (Exception e) {
			log.error("getAllAssetType Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO getAllAssetAttributesByAssetTypeId(Integer assetTypeId) {
		log.info("MasterAssetServiceImpl : getAllAssetAttributesByAssetTypeId info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}

			List<AssetAttribute> assetAttributeList = assetAttributeRepo
					.findAllAssetAttributesByAssetTypeId(assetTypeId);

			if (assetAttributeList.isEmpty()) {
				return buildResponse("NotFound", "No asset attributes found", null);
			} else {
				return buildResponse("Success", "AssetAttributes fetched successfully", assetAttributeList);
			}
		} catch (IllegalArgumentException e) {
			log.error("getAllAssetAttributesByAssetTypeId IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("getAllAssetAttributesByAssetTypeId Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Transactional
	@Override
	public ResponseDTO saveAssetInfo(AssetDTO assetDTO) {
		log.info("MasterAssetServiceImpl:saveAssetInfo info level log message");
		try {
			if (assetDTO.getAssetTypeId() == null || assetDTO.getAssetTypeId() == 0) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or null or invalid");
			}

			if (assetDTO.getAssetAttributeMappingList() == null) {
				throw new IllegalArgumentException(
						"Provide valid AssetAttributeId, it should not be 0 or null or invalid");
			}
			if (assetDTO.getAssetStatus() == null || assetDTO.getAssetStatus().isBlank()) {
				throw new IllegalArgumentException("Provide valid AssetStatus, it should not be null or invalid");
			}
			for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {
				if (assetAttributeMapping.getAsset_attribute_id() == 0
						|| assetAttributeMapping.getAsset_attribute_id().equals("")) {
					throw new IllegalArgumentException(
							"Provide valid AssetAttributeId, it should not be 0 or null or invalid");
				}
				if ((assetAttributeMapping.getAssetAttributeValue() == null
						|| assetAttributeMapping.getAssetAttributeValue().equals(""))
						|| assetAttributeMapping.getAssetAttributeValue().isEmpty()
						|| (assetAttributeMapping.getAssetAttributeValue().length() > 50)) {
					throw new IllegalArgumentException(
							"AssetAttributeValue should not be null or invalid, and it should not be greater than 50 characters");
				}
			}

			Optional<AssetType> existingAssetType = assetTypeRepo.findAssetByAssetTypeId(assetDTO.getAssetTypeId());

			if (existingAssetType.isPresent()) {

				String assetADTId = generateAssetADTId(existingAssetType.get().getAssetName());

				AssetInfo asset = new AssetInfo();
				asset.setAsset_type_id(assetDTO.getAssetTypeId());
				asset.setAssetADT_ID(assetADTId);
				asset.setAssetStatus(assetDTO.getAssetStatus().toUpperCase());
//				asset.setEmpId(null);
				if (assetDTO.getEmpId() == null || assetDTO.getEmpId().equals("")) {
					asset.setEmp_id(null);
				} else {
					asset.setEmp_id(assetDTO.getEmpId());
				}

				AssetInfo savedAssetInfo = assetInfoRepo.save(asset);

				if (savedAssetInfo != null && !savedAssetInfo.equals("")) {

					for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {

						AssetAttributeMapping assetAttributeMappingSaved = new AssetAttributeMapping();

						assetAttributeMappingSaved.setAsset_id(savedAssetInfo.getId());
						assetAttributeMappingSaved.setAsset_attribute_id(assetAttributeMapping.getAsset_attribute_id());
						assetAttributeMappingSaved
								.setAssetAttributeValue(assetAttributeMapping.getAssetAttributeValue().toUpperCase());
						assetAttributeMappingRepo.save(assetAttributeMappingSaved);
					}
					return buildResponse("Success", "AssetInfo saved successfully", null);
				} else {
					return buildResponse("NotSaved", "AssetInfo not saved yet", null);
				}
			} else {
				return buildResponse("NotFound", "AssetType not found", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("saveAssetInfo IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("saveAssetInfo Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO updateAssetAttributeMappingByAssetId(AssetDTO assetDTO) {
		log.info("MasterAssetServiceImpl:updateAssetAttributeMappingByAssetId info level log message");

		try {
			if (assetDTO.getAsset_Id() == null || assetDTO.getAsset_Id().isEmpty() || assetDTO.getAsset_Id().equals("")
					|| assetDTO.getAsset_Id().isBlank()) {
				throw new IllegalArgumentException("Provide valid AssetInfoId, it should not be 0 or invalid or null");
			}
			if (assetDTO.getAssetAttributeMappingList() == null) {
				throw new IllegalArgumentException(
						"Provide valid AssetAttributeId, it should not be 0 or null or invalid");
			}
			for (AssetAttributeMapping assetAttributeMapping : assetDTO.getAssetAttributeMappingList()) {
				if (assetAttributeMapping.getAsset_attribute_id() == 0
						|| assetAttributeMapping.getAsset_attribute_id().equals("")) {
					throw new IllegalArgumentException(
							"Provide valid AssetAttributeId, it should not be 0 or null or invalid");
				}
				if ((assetAttributeMapping.getAssetAttributeValue() == null
						|| assetAttributeMapping.getAssetAttributeValue().equals(""))
						|| assetAttributeMapping.getAssetAttributeValue().isEmpty()
						|| (assetAttributeMapping.getAssetAttributeValue().length() > 50)) {
					throw new IllegalArgumentException(
							"AssetAttributeValue should not be null or invalid, and it should not be greater than 50 characters");
				}
			}

			Optional<AssetInfo> assetInfoExist = assetInfoRepo
					.findAssetInfoByAssetId(Integer.parseInt(assetDTO.getAsset_Id()));

			if (assetInfoExist.isPresent()) {

				Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
						.findMappingListByAssetId(Integer.parseInt(assetDTO.getAsset_Id()));

				if (assetAttributeMappingListExist.isPresent()) {

					for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingListExist.get()) {

						assetAttributeMappingRepo.deleteById(assetAttributeMapping.getId());
					}

					for (AssetAttributeMapping attributeMapping : assetDTO.getAssetAttributeMappingList()) {
						AssetAttributeMapping saveAssetAttributeMapping = new AssetAttributeMapping();

						saveAssetAttributeMapping.setAsset_id(Integer.parseInt(assetDTO.getAsset_Id()));
						saveAssetAttributeMapping.setAsset_attribute_id(attributeMapping.getAsset_attribute_id());
						saveAssetAttributeMapping
								.setAssetAttributeValue(attributeMapping.getAssetAttributeValue().toUpperCase());
						assetAttributeMappingRepo.save(saveAssetAttributeMapping);
					}

					return buildResponse("Success", "AssetAttributeMapping is updated successfully", null);
				}
				return buildResponse("NotUpdated", "AssetInfo not updated yet", null);
			} else {
				return buildResponse("NotFound", "AssetInfo not found", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetAttributeMappingByAssetId IllegalArgumentException: {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeMappingByAssetId Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO deleteAssetInfoById(Integer assetId) {
		log.info("MasterAssetServiceImpl:deleteAssetInfoById info level log message");
		try {
			if (assetId == 0 || assetId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetId, it should not be 0 or invalid or null");
			}
			Optional<AssetInfo> assetInfoExist = assetInfoRepo.findAssetInfoByAssetId(assetId);

			if (assetInfoExist.isPresent()) {

				Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
						.findMappingListByAssetId(assetId);

				if (assetAttributeMappingListExist.isPresent()) {

					for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingListExist.get()) {

						assetAttributeMappingRepo.deleteById(assetAttributeMapping.getId());
					}
				}

				assetInfoRepo.deleteById(assetId);
				return buildResponse("Success", "AssetInfo deleted successfully", null);
			} else {
				return buildResponse("NotFound", "AssetInfo not found", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("deleteAssetInfoById IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetInfoById Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO getAllAssetInfoByAssetTypeIdAndPagination(Integer assetTypeId, int page, int size) {
		log.info("MasterAssetServiceImpl:getAllAssetInfoByAssetTypeIdAndPagination info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
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
					assetDTO.setAsset_Id(assetInfo.getAssetADT_ID());
					assetDTO.setAssetTypeId(assetInfo.getAsset_type_id());

					if (!assetAttributeMappingsList.isEmpty()) {
						assetDTO.setAssetAttributeMappingList(assetAttributeMappingsList);
					} else {
						assetDTO.setAssetAttributeMappingList(null);
					}
					assetDTOList.add(assetDTO);
				}
				return buildResponse("Success", "AssetInfoList retrieved successfully ", assetDTOList);
			} else {
				return buildResponse("NotFound", "AssetInfoList not found ", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("getAllAssetInfoByAssetTypeIdAndPagination IllegalArgumentException : {} ", e.getMessage());

			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAllAssetInfoByAssetTypeIdAndPagination Exception : {} ",
					e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO addAssetType(AssetType assetType) {
		log.info("MasterAssetServiceImpl:addAssetType info level log message");

		try {
			if (assetType == null) {
				throw new IllegalArgumentException("AssetType should not be null or invalid");
			}

			String assetName = assetType.getAssetName();
			String assetAbbreviation = assetType.getAssetAbbreviation();

			String upperAssetName = assetName.toUpperCase();
			String upperAssetAbbreviation = assetAbbreviation.toUpperCase();

			if (assetName == null || assetName.trim().isEmpty() || assetName.length() > 50) {
				throw new IllegalArgumentException(
						"AssetTypeName should not be null, empty, or greater than 50 characters");
			}

			if (assetAbbreviation == null || assetAbbreviation.trim().isEmpty() || assetAbbreviation.length() < 2
					|| assetAbbreviation.length() > 10) {
				throw new IllegalArgumentException(
						"AssetAbbreviation should not be null, empty, or it should not be less than 2 or greater than 10 characters");
			}

			Optional<AssetType> assetTypeExistByName = assetTypeRepo.findByAssetName(upperAssetName);
			if (assetTypeExistByName.isPresent()) {
				return buildResponse("AlreadyExist",
						"This AssetTypeName is already exists, please enter a unique asset type name", null);
			}

			Optional<AssetType> assetTypeExistByAbbreviation = assetTypeRepo
					.findByAssetAbbreviation(upperAssetAbbreviation);
			if (assetTypeExistByAbbreviation.isPresent()) {
				return buildResponse("AlreadyExist",
						"This AssetAbbreviation is already assigned to another asset type, please enter a unique asset abbreviation",
						null);
			}

			assetType.setAssetName(upperAssetName);
			assetType.setAssetAbbreviation(upperAssetAbbreviation);
			assetTypeRepo.save(assetType);

			return buildResponse("Success", "AssetType saved successfully", null);

		} catch (IllegalArgumentException e) {
			log.error("addAssetType IllegalArgumentException: {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: addAssetType Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO getAssetTypeById(Integer assetTypeId) {
		log.info("MasterAssetServiceImpl:getAssetTypeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}

			Optional<AssetType> assetType = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);

			if (assetType.isEmpty()) {
				return buildResponse("NotFound", "AssetType not found", null);
			} else {
				return buildResponse("Success", "AssetType fetched successfully", assetType.get());
			}
		} catch (IllegalArgumentException e) {
			log.error("getAssetTypeById IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAssetTypeById Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO updateAssetTypeById(Integer assetTypeId, AssetType assetType) {
		log.info("MasterAssetServiceImpl:updateAssetTypeById info level log message");

		try {
			if (assetTypeId == null || assetTypeId == 0) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}

			String assetName = assetType.getAssetName();
			String assetAbbreviation = assetType.getAssetAbbreviation();

			if (assetName == null || assetName.trim().isEmpty() || assetName.length() > 50) {
				throw new IllegalArgumentException(
						"AssetTypeName should not be null, invalid, or greater than 50 characters");
			}

			if (assetAbbreviation == null || assetAbbreviation.trim().isEmpty() || assetAbbreviation.length() < 2
					|| assetAbbreviation.length() > 10) {
				throw new IllegalArgumentException(
						"AssetAbbreviation should not be null, empty, or it should not be less than 2 or greater than 10 characters");
			}

			Optional<AssetType> existingAssetType = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);

			if (existingAssetType.isPresent()) {
				AssetType assetToUpdate = existingAssetType.get();
				if (assetName != null && !assetName.trim().isEmpty()) {
					assetToUpdate.setAssetName(assetName.toUpperCase());
				}
				if (assetAbbreviation != null && !assetAbbreviation.trim().isEmpty()) {
					assetToUpdate.setAssetAbbreviation(assetAbbreviation.toUpperCase());
				}

				assetTypeRepo.save(assetToUpdate);
				return buildResponse("Success", "AssetType updated successfully", null);
			} else {
				return buildResponse("NotFound", "AssetType not found", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetTypeById IllegalArgumentException: {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetTypeById Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO deleteAssetTypeById(Integer assetTypeId) {
		log.info("MasterAssetServiceImpl:deleteAssetTypeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}

			Optional<List<AssetInfo>> assetInfoListExist = assetInfoRepo.findAssetInfoListByAssetTypeId(assetTypeId);
			if (assetInfoListExist.isPresent() && !assetInfoListExist.get().isEmpty()) {
				for (AssetInfo assetInfo : assetInfoListExist.get()) {
					Optional<List<AssetAttributeMapping>> assetAttributeMappingListExist = assetAttributeMappingRepo
							.findAssetAttributeMappingListByAssetInfoId(assetInfo.getId());
					if (assetAttributeMappingListExist.isPresent() && !assetAttributeMappingListExist.get().isEmpty()) {

						return buildResponse("AlreadyAssociated", "This Asset is already in use, you can't delete this",
								null);
					}
				}
				return buildResponse("AlreadyAssociated", "This Asset is already in use, you can't delete this", null);
			}

			Optional<List<AssetAttribute>> assetAttributeListExist = assetAttributeRepo
					.findAssetAttributeByAssetTypeId(assetTypeId);
			if (assetAttributeListExist.isPresent() && !assetAttributeListExist.get().isEmpty()) {
				return buildResponse("AlreadyAssociated", "This Asset is already in use, you can't delete this", null);
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

				return buildResponse("Success", "AssetType deleted successfully", null);
			} else {
				return buildResponse("NotFound", "AssetType not found", null);
			}

		} catch (IllegalArgumentException e) {
			log.error("deleteAssetTypeById IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetTypeById Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO addAssetAttributesByAssetTypeId(Integer assetTypeId, String assetAttributeName) {
		log.info("MasterAssetServiceImpl:addAssetAttributesByAssetTypeId info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}
			if ((assetAttributeName == null || assetAttributeName.equals("")) || (assetAttributeName.length() > 50)) {
				throw new IllegalArgumentException(
						"AssetAttributeName should not be null or invalid, it should not be greater than 50 characters ");
			}

			Optional<AssetType> existingAssetType = assetTypeRepo.findAssetByAssetTypeId(assetTypeId);
			if (existingAssetType.isPresent()) {

				if (assetAttributeName != null && !assetAttributeName.equals("")) {

					AssetAttribute assetAttributeSave = new AssetAttribute();
					assetAttributeSave.setAssetAttributeName(assetAttributeName.toUpperCase());
					assetAttributeSave.setAsset_type_id(assetTypeId);

					assetAttributeRepo.save(assetAttributeSave);
					return buildResponse("Success", "AssetAttribute saved successfully", null);

				} else {
					return buildResponse("NotSaved", "AssetAttribute not saved yet", null);
				}
			} else {
				return buildResponse("NotFound", "AssetType not found", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("addAssetAttributesByAssetTypeId IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: addAssetAttributesByAssetTypeId Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO updateAssetAttributeById(Integer assetAttributeId, Integer assetTypeId,
			String assetAttributeName) {
		log.info("MasterAssetServiceImpl:updateAssetAttributeById info level log message");
		try {
			if (assetTypeId == 0 || assetTypeId.equals("")) {
				throw new IllegalArgumentException("Provide valid AssetTypeId, it should not be 0 or invalid or null");
			}
			if (assetAttributeId == 0 || assetAttributeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide valid AssetAttributeId, it should not be 0 or invalid or null");
			}
			if ((assetAttributeName == null || assetAttributeName.equals("")) || (assetAttributeName.length() > 50)) {
				throw new IllegalArgumentException(
						"AssetAttributeName should not be null or invalid, and it should not be greater than 50 characters ");
			}

			Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo.findById(assetAttributeId);

			if (assetAttributeExist.isPresent()) {

				assetAttributeExist.get().setAssetAttributeName(assetAttributeName.toUpperCase());
				assetAttributeExist.get().getAssetType();
				assetAttributeExist.get().setAsset_type_id(assetTypeId);

				assetAttributeRepo.save(assetAttributeExist.get());
				return buildResponse("Success", "AssetAttribute updated successfully", null);
			} else {
				return buildResponse("NotFound", "AssetAttribute not found ", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("updateAssetAttributeById IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeById Exception : {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO deleteAssetAttributeById(Integer assetAttributeId) {
		log.info("MasterAssetServiceImpl:deleteAssetAttributeById info level log message");
		try {
			if (assetAttributeId == 0 || assetAttributeId.equals("")) {
				throw new IllegalArgumentException(
						"Provide valid AssetAttributeId,it should not be 0 or invalid or null");
			}

			Optional<AssetAttribute> assetAttributeExist = assetAttributeRepo.findById(assetAttributeId);

			if (assetAttributeExist.isPresent()) {

				List<AssetAttributeMapping> mappingListExist = assetAttributeMappingRepo
						.findMappingListByAtrributeId(assetAttributeId).orElse(Collections.emptyList());
				if (!mappingListExist.isEmpty()) {
					return buildResponse("AlreadyAssociated",
							"Asset is already created using this attribute, you can't delete this", null);
				}
				assetAttributeRepo.deleteById(assetAttributeId);
				return buildResponse("Success", "AssetAttribute deleted successfully", null);

			} else {
				return buildResponse("NotFound", "AssetAttribute not found ", null);
			}
		} catch (IllegalArgumentException e) {
			log.error("deleteAssetAttributeById IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetAttributeById Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO searchEmployeeDetails(String firstName, String lastName, String empAdtId, String firstLetter,
			int page, int size) {
		log.info("MasterAssetServiceImpl:searchEmployeeDetails info level log message");
		try {
			if (firstName != null && !firstName.isEmpty() && containsDigit(firstName)) {
				throw new IllegalArgumentException("First name cannot contain digits or numbers");
			} else if (firstName == "") {
				throw new IllegalArgumentException("First name cannot be empty");
			}
			if (lastName != null && !lastName.isEmpty() && containsDigit(lastName)) {
				throw new IllegalArgumentException("Last name cannot contain digits or numbers");
			} else if (lastName == "") {
				throw new IllegalArgumentException("Last name cannot be empty");
			}
			if ((empAdtId != null && !empAdtId.isEmpty()) && !containsDigit(empAdtId)) {
				throw new IllegalArgumentException("Provide valid EmployeeADT_Id");
			} else if (empAdtId == "") {
				throw new IllegalArgumentException("EmployeeADT_Id cannot be empty or null");
			}

			Pageable pageable = PageRequest.of(page, size);
			Specification<Employee> spec = Specification.where(null);

			if (firstName != null && !firstName.isEmpty()) {
				spec = spec.or(
						(root, query, cb) -> cb.like(cb.lower(root.get("firstName")), firstName.toLowerCase() + "%"));
			}
			if (lastName != null && !lastName.isEmpty()) {
				spec = spec
						.or((root, query, cb) -> cb.like(cb.lower(root.get("lastName")), lastName.toLowerCase() + "%"));
			}
			if (empAdtId != null && !empAdtId.isEmpty()) {
				spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("adtId")), empAdtId.toLowerCase() + "%")); // Changed
			}

			Page<Employee> emp = employeeRepo.findAll(spec, pageable);
			if (emp.isEmpty() || emp == null) {
				return buildResponse("NotFound", "Employee not found", null);
			}

			List<EmployeeDTO> empDTOList = new ArrayList<>();
			for (Employee employee : emp) {
				EmployeeDTO empDTO = new EmployeeDTO();

				empDTO.setFirstName(employee.getFirstName());
				empDTO.setLastName(employee.getLastName());
				empDTO.setEmpAdtId(employee.getAdtId());
				empDTOList.add(empDTO);
			}
			return buildResponse("Success", "Employee fetched successfully", empDTOList);

		} catch (IllegalArgumentException e) {
			log.error("searchEmployeeDetails IllegalArgumentException : {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: searchEmployeeDetails Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

	private boolean containsDigit(String str) {
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseDTO getAllAssignedAssetsByEmpId(Integer empId) {
		log.info("MasterAssetServiceImpl:getAllAssignedAssetsByEmpId info level log message");
		try {
			if (empId == null || empId == 0) {
				throw new IllegalArgumentException("Provide a valid EmployeeId, it should not be null or blank");
			}

			Optional<Employee> employeeExist = employeeRepo.findById(empId);
			if (employeeExist.isEmpty()) {
				return buildResponse("NotFound", "Employee not found", null);
			}

			List<AssetInfoDTO> assetInfoDTOList = new ArrayList<>();
			List<AssetInfo> assetInfoList = assetInfoRepo.findAssetsByEmpId(empId);

			if (assetInfoList.isEmpty()) {
				return buildResponse("NotFound", "Assets not assigned yet", assetInfoDTOList);
			}

			for (AssetInfo assetInfo : assetInfoList) {
				AssetInfoDTO assetInfoDTO = new AssetInfoDTO();
				assetInfoDTO.setAssetADTId(assetInfo.getAssetADT_ID());
				assetInfoDTO.setAssetStatus(assetInfo.getAssetStatus());

				Optional<AssetType> assetTypeExist = assetTypeRepo.findById(assetInfo.getAsset_type_id());
				assetTypeExist.ifPresent(assetType -> assetInfoDTO.setAssetType(assetType.getAssetName()));

				List<AssetAttributeMappingDTO> assetAttributeMappingDTOList = new ArrayList<>();
				List<AssetAttributeMapping> attributeMappingList = assetAttributeMappingRepo
						.findAssetMappingListByAssetId(assetInfo.getId());

				for (AssetAttributeMapping attributeMapping : attributeMappingList) {
					AssetAttributeMappingDTO assetAttributeMappingDTO = new AssetAttributeMappingDTO();
					assetAttributeMappingDTO
							.setAssetAttributeName(attributeMapping.getAssetAttribute().getAssetAttributeName());
					assetAttributeMappingDTO.setAssetAttributeValue(attributeMapping.getAssetAttributeValue());
					assetAttributeMappingDTOList.add(assetAttributeMappingDTO);
				}

				assetInfoDTO.setAssetAttributeMappingList(assetAttributeMappingDTOList);
				assetInfoDTOList.add(assetInfoDTO);
			}

			return buildResponse("Success", "All assigned assets fetched successfully", assetInfoDTOList);

		} catch (IllegalArgumentException e) {
			log.error("getAllAssignedAssetsByEmpId IllegalArgumentException: {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAllAssignedAssetsByEmpId Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

	@Override
	public ResponseDTO assignAllAssetsToEmp(AssignAssetsDTO assignAssetsDTO) {
		log.info("MasterAssetServiceImpl:assignAllAssetsToEmp info level log message");
		try {
			if (assignAssetsDTO.getEmpAdtId() == null || assignAssetsDTO.getEmpAdtId().trim().isEmpty()) {
				throw new IllegalArgumentException("Provide valid EmpAdtId, it should not be null or blank");
			}
			if (assignAssetsDTO.getAssetAdtIdList() == null || assignAssetsDTO.getAssetAdtIdList().isEmpty()) {
				throw new IllegalArgumentException("Provide valid AssetAdtIdList, it should not be null or empty");
			}

			Optional<Employee> employeeOpt = employeeRepo.findEmpByAdtId(assignAssetsDTO.getEmpAdtId());
			if (employeeOpt.isEmpty()) {
				return buildResponse("NotFound", "Employee not found", null);
			}
			Employee employee = employeeOpt.get();

			List<AssetInfo> assetInfoList = new ArrayList<>();
			for (String assetAdtId : assignAssetsDTO.getAssetAdtIdList()) {
				Optional<AssetInfo> assetOpt = assetInfoRepo.findAssetsByAdtId(assetAdtId);
				if (assetOpt.isEmpty()) {
					return buildResponse("NotFound", "AssetAdtId: " + assetAdtId + " not found", null);
				}
				assetInfoList.add(assetOpt.get());
			}

			for (AssetInfo asset : assetInfoList) {
				if ((asset.getEmp_id() != null) && (!asset.getEmp_id().equals(employee.getEmployeeId()))) {
					Integer empId = asset.getEmp_id();
					Optional<Employee> empExist = employeeRepo.findEmployeeById(empId);
					String empName = empExist.get().getFirstName() + " " + empExist.get().getLastName();
					return buildResponse("AlreadyExist", "AssetAdtId:" + asset.getAssetADT_ID()
							+ " is already assigned to Employee:" + empName + ", please select another asset", null);
				}
			}
			AssetInfo updatedAsset = new AssetInfo();
			for (AssetInfo asset : assetInfoList) {
				asset.setEmp_id(employee.getEmployeeId());
				asset.setAssetStatus("ALLOTTED");
				updatedAsset = assetInfoRepo.save(asset);
			}
			if (updatedAsset == null || updatedAsset.equals("")) {
				return buildResponse("NotSaved", "All assets not assigned", null);
			}

			return buildResponse("Success", "All assets assigned successfully", null);
		} catch (IllegalArgumentException e) {
			log.error("assignAllAssetsToEmp IllegalArgumentException: {} ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: assignAllAssetsToEmp Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

	@Override
	public ResponseDTO getAssetInfoByAssetAdtId(String assetAdtId) {
		log.info("MasterAssetServiceImpl:getAssetInfoByAssetAdtId info level log message");

		if (assetAdtId == null || assetAdtId.isEmpty() || assetAdtId.isBlank()) {
			throw new IllegalArgumentException("Provide valid AssetAdtId, it should not be null or blank");
		}

		try {
			AssetInfo assetInfo = assetInfoRepo.findAssetsByAdtId(assetAdtId)
					.orElseThrow(() -> new IllegalArgumentException("AssetAdtId:" + assetAdtId + " not found"));

			AssetType assetType = assetTypeRepo.findById(assetInfo.getAsset_type_id())
					.orElseThrow(() -> new IllegalArgumentException("AssetType not found"));

			if ("ALLOTTED".equalsIgnoreCase(assetInfo.getAssetStatus())) {
				Employee emp = employeeRepo.findEmployeeById(assetInfo.getEmp_id())
						.orElseThrow(() -> new IllegalArgumentException("Employee not found"));
				String empName = emp.getFirstName() + " " + emp.getLastName();
				return buildResponse("AlreadyExist", "AssetAdtId:" + assetAdtId + " is already assigned to Employee:"
						+ empName + ", please select another asset", null);
			}

			if ("DEFECTIVE".equalsIgnoreCase(assetInfo.getAssetStatus())
					|| "DISCARDED".equalsIgnoreCase(assetInfo.getAssetStatus())) {
				return buildResponse("Success", "AssetAdtId:" + assetAdtId + " is either defective or discarded", null);
			}

			List<AssetAttributeMappingDTO> assetAttributeMappingDTOList = new ArrayList<>();
			List<AssetAttributeMapping> assetAttributeMappingsList = assetAttributeMappingRepo
					.findAssetMappingListByAssetId(assetInfo.getId());

			for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingsList) {
				AssetAttributeMappingDTO assetAttributeMappingDTO = new AssetAttributeMappingDTO();
				AssetAttribute assetAttribute = assetAttributeRepo
						.findById(assetAttributeMapping.getAsset_attribute_id()).orElse(null);
				if (assetAttribute != null) {
					assetAttributeMappingDTO.setAssetAttributeName(assetAttribute.getAssetAttributeName());
					assetAttributeMappingDTO.setAssetAttributeValue(assetAttributeMapping.getAssetAttributeValue());
				}
				assetAttributeMappingDTOList.add(assetAttributeMappingDTO);
			}

			AssetInfoDTO assetDTO = new AssetInfoDTO();
			if (assetInfo.getEmp_id() == null) {
				assetDTO.setEmpADTId(null);
			}
//			assetDTO.setEmpId(employeeRepo.findById(assetInfo.getEmp_id()).map(Employee::getAdtId).orElse(null));
			assetDTO.setAssetADTId(assetInfo.getAssetADT_ID());
			assetDTO.setAssetType(assetType.getAssetName());
			assetDTO.setAssetStatus(assetInfo.getAssetStatus());
			assetDTO.setAssetAttributeMappingList(assetAttributeMappingDTOList);

			return buildResponse("Success", "This asset available to assign", assetDTO);

		} catch (IllegalArgumentException e) {
			log.error("getAssetInfoByAssetAdtId IllegalArgumentException: ", e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAssetInfoByAssetAdtId Exception: {} ", e.getMessage());
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

//	//chlta code ------------------------
//	@Override
//	public ResponseDTO assignAllAssetsToEmp(AssignAssetsDTO assignAssetsDTO) {
//		log.info("MasterAssetServiceImpl:assignAllAssetsToEmp info level log message");
//		try {
//			if (assignAssetsDTO.getEmpAdtId() == null || assignAssetsDTO.getEmpAdtId().trim().isEmpty()) {
//				throw new IllegalArgumentException("Provide valid EmpAdtId, it should not be null or blank");
//			}
//			if (assignAssetsDTO.getAssetAdtIdList() == null || assignAssetsDTO.getAssetAdtIdList().isEmpty()) {
//				throw new IllegalArgumentException("Provide valid AssetAdtIdList, it should not be null or empty");
//			}
//
//			Optional<Employee> employeeExist = employeeRepo.findEmpByAdtId(assignAssetsDTO.getEmpAdtId());
//			if (employeeExist.isEmpty()) {
//				return buildResponse("NotFound", "Employee not found", null);
//			}
//
//			List<String> assetAdtIdList = assignAssetsDTO.getAssetAdtIdList();
//			List<AssetInfo> assetInfoList = new ArrayList<>();
//			Optional<AssetInfo> assetInfoExist = null;
//			for (String assetAdtId : assetAdtIdList) {
//				assetInfoExist = assetInfoRepo.findAssetsByAdtId(assetAdtId);
//				if (assetInfoExist.isEmpty()) {
//					return buildResponse("NotFound", "AssetAdtId: " + assetAdtId + " not found", null);
//				}
//				assetInfoList.add(assetInfoExist.get());
//			}
//
////			for (AssetInfo assetInfo : assetInfoList) {
////				if (assetInfo.getEmp_id() != null
////						&& !assetInfo.getEmp_id().equals(employeeExist.get().getEmployeeId())) {
////					String empName = employeeExist.get().getFirstName() + " " + employeeExist.get().getLastName();
////					return buildResponse(
////							"AlreadyExist", "AssetAdtId: " + assetInfo.getAssetADT_ID()
////									+ " is already assigned to Employee: " + empName + ", please select another asset",
////							null);
////				}
////			}
//			if (assetInfoExist.isPresent()) {
//				List<AssetInfo> asset = assetInfoRepo.findAssignedAssetList(assetInfoExist.get().getEmp_id());
////if (!asset.isEmpty()) 
//				if (!asset.isEmpty()) {
//					for (AssetInfo assetAssigned : asset) {
//						if(assetAssigned.getAssetADT_ID() == assetAdtIdList.get().getAssetADT_ID()) {
//						String empName = employeeExist.get().getFirstName() + " " + employeeExist.get().getLastName();
//						return buildResponse(
//								"AlreadyExist", "AssetAdtId:" + assetAssigned.getAssetADT_ID()
//										+ " is already assigned to Employee:" + empName + " ,please select other asset",
//								null);
//						}
//					}
//				}
//			}
//
//			AssetInfo updatedAsset = new AssetInfo();
//			for (AssetInfo assetInfoUpdated : assetInfoList) {
//				assetInfoUpdated.setEmp_id(employeeExist.get().getEmployeeId());
//				assetInfoUpdated.setAssetStatus("ALLOTTED");
//				updatedAsset = assetInfoRepo.save(assetInfoUpdated);
//			}
//
//			if (updatedAsset == null || updatedAsset.equals("")) {
//				return buildResponse("NotSaved", "All assets not assigned", null);
//			}
//			return buildResponse("Success", "All assets assigned successfully", null);
//
//		} catch (IllegalArgumentException e) {
//			log.error("assignAllAssetsToEmp IllegalArgumentException: " + e.getMessage(), e);
//			return buildResponse("Failed", e.getMessage(), null);
//		} catch (Exception e) {
//			log.error("MasterAssetServiceImpl: assignAllAssetsToEmp Exception: " + e, e);
//			return buildResponse("Failed", "Internal server error occurred", null);
//		}
//	}

}
