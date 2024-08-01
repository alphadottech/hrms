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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adt.hrms.model.AssetAttribute;
import com.adt.hrms.model.AssetAttributeMapping;
import com.adt.hrms.model.AssetEmployeeMapping;
import com.adt.hrms.model.AssetInfo;
import com.adt.hrms.model.AssetType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.repository.AssetAttributeMappingRepo;
import com.adt.hrms.repository.AssetAttributeRepo;
import com.adt.hrms.repository.AssetEmployeeMappingRepo;
import com.adt.hrms.repository.AssetInfoRepo;
import com.adt.hrms.repository.AssetTypeRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.request.AssetAttributeMappingDTO;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.AssetInfoDTO;
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

	@Autowired
	private AssetEmployeeMappingRepo assetEmpMappingRepo;

//	@Autowired
//	private MasterAssetRepository repo;

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
			log.error("getAllAssetType Exception : " + e);
			e.printStackTrace();
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
			log.error("getAllAssetAttributesByAssetTypeId IllegalArgumentException : " + e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("getAllAssetAttributesByAssetTypeId Exception : " + e);
			e.printStackTrace();
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
			e.printStackTrace();
			log.error("saveAssetInfo IllegalArgumentException : " + e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("saveAssetInfo Exception : " + e);
			e.printStackTrace();
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
			log.error("updateAssetAttributeMappingByAssetId IllegalArgumentException: " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeMappingByAssetId Exception: " + e);
			e.printStackTrace();
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
			log.error("deleteAssetInfoById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetInfoById Exception : " + e);
			e.printStackTrace();
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
			log.error("getAllAssetInfoByAssetTypeIdAndPagination IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAllAssetInfoByAssetTypeIdAndPagination Exception : " + e);
			e.printStackTrace();
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

	@Override
	public ResponseDTO getAllAssignedAssetsToEmpById(String empId) {
		log.info("MasterAssetServiceImpl:getAllAssignedAssetsToEmpById info level log message");

		try {
			if (empId == null || empId.isBlank()) {
				throw new IllegalArgumentException("Provide a valid EmployeeId, it should not be null or blank");
			}

			Optional<Employee> employeeExist = employeeRepo.findById(Integer.parseInt(empId));
			if (employeeExist.isEmpty() || employeeExist.get() == null) {
				return buildResponse("NotFound", "Employee not found", null);
			}

			Optional<List<AssetEmployeeMapping>> assetEmpMappingExist = assetEmpMappingRepo
					.findAssetsByEmpId(Integer.parseInt(empId));
			if (assetEmpMappingExist.isEmpty() || assetEmpMappingExist.get().isEmpty()) {
				return buildResponse("Success", "Assets not assigned yet", null);
			}

			List<String> assetADTIds = new ArrayList<>();
			for (AssetEmployeeMapping empMapping : assetEmpMappingExist.get()) {
				Optional<AssetInfo> assetInfoOptional = assetInfoRepo.findById(empMapping.getAsset_id());

				if (assetInfoOptional.isEmpty()) {
					return buildResponse("NotFound", "Asset not found for asset ID: " + empMapping.getAsset_id(), null);
				}
				assetADTIds.add(assetInfoOptional.get().getAssetADT_ID());
			}
			return buildResponse("Success", "All assigned assets fetched successfully", assetADTIds);

		} catch (IllegalArgumentException e) {
			log.error("getAllAssignedAssetsToEmpById IllegalArgumentException: " + e.getMessage(), e);
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAllAssignedAssetsToEmpById Exception: " + e, e);
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

	@Override
	public ResponseDTO getAssetInfoById(Integer assetId) {
		log.info("MasterAssetServiceImpl:getAssetInfoById info level log message");
		try {
			if (assetId == null || assetId == 0) {
				throw new IllegalArgumentException("Provide a valid AssetInfoId, it should not be 0 or null");
			}

			Optional<AssetInfo> assetInfoOptional = assetInfoRepo.findById(assetId);
			AssetInfo assetInfo = assetInfoOptional.get();
			if (assetInfoOptional.isEmpty()) {
				return buildResponse("NotFound", "AssetInfo not found", null);
			}

			List<AssetAttributeMapping> assetAttributeMappingsList = assetAttributeMappingRepo
					.findAssetMappingListByAssetId(assetInfo.getId());
			if (assetAttributeMappingsList.isEmpty()) {
				return buildResponse("NotFound", "AssetAttributeMapping not found", null);
			}

			Optional<AssetType> assetTypeOptional = assetTypeRepo.findById(assetInfo.getAsset_type_id());
			AssetType assetType = assetTypeOptional.get();
			if (assetTypeOptional.isEmpty()) {
				return buildResponse("NotFound", "AssetType not found", null);
			}

			List<AssetAttributeMappingDTO> assetAttributeMappingDTOList = new ArrayList<>();
			for (AssetAttributeMapping assetAttributeMapping : assetAttributeMappingsList) {
				AssetAttributeMappingDTO assetAttributeMappingDTO = new AssetAttributeMappingDTO();

				Optional<AssetAttribute> assetAttributeOptional = assetAttributeRepo
						.findById(assetAttributeMapping.getAsset_attribute_id());
				if (assetAttributeOptional.isPresent()) {
					AssetAttribute assetAttribute = assetAttributeOptional.get();
					assetAttributeMappingDTO.setAssetAttributeName(assetAttribute.getAssetAttributeName());
				}

				assetAttributeMappingDTO.setAssetAttributeValue(assetAttributeMapping.getAssetAttributeValue());
				assetAttributeMappingDTOList.add(assetAttributeMappingDTO);
			}

			AssetInfoDTO assetInfoDTO = new AssetInfoDTO();

			Optional<AssetEmployeeMapping> assetEmployeeMappingExist = assetEmpMappingRepo.findByAssetId(assetId);
			Optional<Employee> employeeExist = employeeRepo.findById(assetEmployeeMappingExist.get().getEmpId());
			if (assetEmployeeMappingExist.isPresent() && employeeExist.isPresent()) {
				assetInfoDTO.setEmpId(employeeExist.get().getAdtId());
			} else {
				assetInfoDTO.setEmpId(null);
			}
			assetInfoDTO.setAssetADTId(assetInfo.getAssetADT_ID());
			assetInfoDTO.setAssetType(assetType.getAssetName());
			assetInfoDTO.setAssetAbbreviation(assetType.getAssetAbbreviation());
			assetInfoDTO.setAssetStatus(assetInfo.getAssetStatus());
			assetInfoDTO.setAssetAttributeMappingList(assetAttributeMappingDTOList);

			return buildResponse("Success", "AssetInfo fetched successfully", assetInfoDTO);

		} catch (IllegalArgumentException e) {
			log.error("getAssetInfoById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAssetInfoById Exception : " + e);
			e.printStackTrace();
			return buildResponse("Failed", "Internal server error occurred", null);
		}
	}

	@Override
	public ResponseDTO assignAssetToEmp(AssetEmployeeMapping assetEmployeeMapping) {
		log.info("MasterAssetServiceImpl:assignAssetToEmp info level log message");
		try {
			if (assetEmployeeMapping.getAsset_id() == 0 || assetEmployeeMapping.getAsset_id().equals("")
					|| assetEmployeeMapping.getAsset_id() == null) {
				throw new IllegalArgumentException("Provide valid AssetId, it should not be 0 or invalid or null");
			}
			if (assetEmployeeMapping.getEmpId() == 0 || assetEmployeeMapping.getEmpId().equals("")
					|| assetEmployeeMapping.getEmpId() == null) {
				throw new IllegalArgumentException("Provide valid EmployeeId, it should not be 0 or invalid or null");
			}

			Optional<Employee> employeeExist = employeeRepo.findEmployeeById(assetEmployeeMapping.getEmpId());
			if (!employeeExist.isPresent()) {
				return buildResponse("NotFound", "Employee not Found", null);
			}

			Optional<AssetInfo> assetInfoExist = assetInfoRepo
					.findAssetInfoByAssetId(assetEmployeeMapping.getAsset_id());
			if (!assetInfoExist.isPresent()) {
				return buildResponse("NotFound", "Asset data not found", null);
			}

			Optional<AssetEmployeeMapping> assetEmpMappingExist = assetEmpMappingRepo
					.findByAssetId(assetEmployeeMapping.getAsset_id());
			if (assetEmpMappingExist.isPresent()) {
				return buildResponse("AlreadyExist", "This asset is already assigned, please select another asset",
						null);
			}

			AssetEmployeeMapping assigned = assetEmpMappingRepo.save(assetEmployeeMapping);
			if (assigned == null || assigned.equals("")) {
				return buildResponse("NotSaved", "Asset not assigned yet", null);
			}
			return buildResponse("Success", "Asset assigned successfully", null);

		} catch (IllegalArgumentException e) {
			log.error("assignAssetToEmp IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: assignAssetToEmp Exception : " + e);
			e.printStackTrace();
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
			e.printStackTrace();
			log.error("addAssetType IllegalArgumentException: " + e.getMessage());
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MasterAssetServiceImpl: addAssetType Exception: ", e);
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
			log.error("getAssetTypeById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: getAssetTypeById Exception : " + e);
			e.printStackTrace();
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
			log.error("updateAssetTypeById IllegalArgumentException: " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetTypeById Exception: ", e);
			e.printStackTrace();
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
			log.error("deleteAssetTypeById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetTypeById Exception: " + e);
			e.printStackTrace();
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
			log.error("addAssetAttributesByAssetTypeId IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: addAssetAttributesByAssetTypeId Exception : " + e);
			e.printStackTrace();
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
			log.error("updateAssetAttributeById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: updateAssetAttributeById Exception : " + e);
			e.printStackTrace();
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
			log.error("deleteAssetAttributeById IllegalArgumentException : " + e.getMessage());
			e.printStackTrace();
			return buildResponse("Failed", e.getMessage(), null);
		} catch (Exception e) {
			log.error("MasterAssetServiceImpl: deleteAssetAttributeById Exception : " + e);
			e.printStackTrace();
			return buildResponse("Failed", "Internal server error occured", null);
		}
	}

//	@Override
//	public ResponseDTO saveAssetDetailsWithAttributes(CreateAssetDTO createAssetDTO) {
//		ResponseDTO responseDTO = new ResponseDTO();
//		log.info("MasterAssetServiceImpl:masterAsset:saveAssetDetailsWithAttributes info level log message");
//		try {
//
//			if (createAssetDTO == null || createAssetDTO.equals("")) {
//				throw new IllegalArgumentException("CreateAssetDTO should not be null or invalid");
//			}
//			if (createAssetDTO.getAssetName() == null || createAssetDTO.getAssetName().equals("")) {
//				throw new IllegalArgumentException("Asset Name should not be null or invalid");
//			}
//			if (createAssetDTO.getAssetAttributeList() == null || createAssetDTO.getAssetAttributeList().equals("")) {
//				throw new IllegalArgumentException("Asset Attribute list should not be null or invalid");
//			}
//			for (AssetAttribute assetAttribute : createAssetDTO.getAssetAttributeList()) {
//				if (assetAttribute.getAssetAttributeName() == null
//						|| assetAttribute.getAssetAttributeName().equals("")) {
//					throw new IllegalArgumentException("Asset Attribute name should not be null or invalid");
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

//	@Override
//	public boolean saveMasterAsset(MasterAsset asset) {
//		if (!AssetUtility.checkvalidate(asset.getAssetUser())) {
//			throw new IllegalArgumentException("invalid Asset User...");
//		}
//		if (!AssetUtility.validateName(asset.getAssetName())) {
//			throw new IllegalArgumentException("invalid Asset Name..");
//		}
//		if (!AssetUtility.validateId(asset.getAssetId())) {
//			throw new IllegalArgumentException("invalid Asset ID ");
//		}
//		if (!AssetUtility.validateId(asset.getAssetNo())) {
//			throw new IllegalArgumentException("invalid Asset Number");
//		}
//		if (!AssetUtility.checkvalidate(asset.getAssetType())) {
//			throw new IllegalArgumentException("invalid Asset Type...");
//		}
//		if (!AssetUtility.validateProcessor(asset.getProcessor())) {
//			throw new IllegalArgumentException("invalid Processor Details");
//		}
//		if (!AssetUtility.validateRAM(asset.getRam())) {
//			throw new IllegalArgumentException("invalid RAM Details");
//		}
//		if (!AssetUtility.validateDiskType(asset.getDiskType())) {
//			throw new IllegalArgumentException("invalid Disc Type Details");
//		}
//		if (!AssetUtility.validateProcessor(asset.getOperatingSystem())) {
//			throw new IllegalArgumentException("invalid Operating System Details");
//		}
//		if (!AssetUtility.validateProcessor(asset.getWarrenty())) {
//			throw new IllegalArgumentException("invalid Warranty Details");
//		}
//		MasterAsset masterasset = repo.save(asset);
//
//		return masterasset != null;
//	}
//
//	@Override
//	public MasterAsset TakeAssetById(Integer id) {
//		Optional<MasterAsset> getById = repo.findById(id);
//		return getById.get();
//	}
//
//	@Override
//	public List<MasterAsset> SearchByAssetUser(String assetUser) {
//		return repo.findByAssetUser(assetUser);
//	}
//
//	@Override
//	public List<MasterAsset> SearchByStatus(String status) {
//		return repo.findByStatus(status);
//	}
//
//	@Override
//	public List<MasterAsset> SearchByAssetType(String assetType) {
//		return repo.findByAssetType(assetType);
//	}
//
//	@Override
//	public String updateMasterAssetById(MasterAsset masterAsset) {
//		if (!AssetUtility.checkvalidate(masterAsset.getAssetUser())) {
//			throw new IllegalArgumentException("invalid Asset User...");
//		}
//		if (!AssetUtility.validateName(masterAsset.getAssetName())) {
//			throw new IllegalArgumentException("invalid Asset Name..");
//		}
//		if (!AssetUtility.validateId(masterAsset.getAssetId())) {
//			throw new IllegalArgumentException("invalid Asset ID ");
//		}
//		if (!AssetUtility.validateId(masterAsset.getAssetNo())) {
//			throw new IllegalArgumentException("invalid Asset Number");
//		}
//		if (!AssetUtility.checkvalidate(masterAsset.getAssetType())) {
//			throw new IllegalArgumentException("invalid Asset Type...");
//		}
//		if (!AssetUtility.validateProcessor(masterAsset.getProcessor())) {
//			throw new IllegalArgumentException("invalid Processor Details");
//		}
//		if (!AssetUtility.validateRAM(masterAsset.getRam())) {
//			throw new IllegalArgumentException("invalid RAM Details");
//		}
//		if (!AssetUtility.validateDiskType(masterAsset.getDiskType())) {
//			throw new IllegalArgumentException("invalid Disc Type Details");
//		}
//		if (!AssetUtility.validateProcessor(masterAsset.getOperatingSystem())) {
//			throw new IllegalArgumentException("invalid Operating System Details");
//		}
//		if (!AssetUtility.validateProcessor(masterAsset.getWarrenty())) {
//			throw new IllegalArgumentException("invalid Warranty Details");
//		}
//		MasterAsset dbAsset = repo.findAssetById(masterAsset.getId());
//		if (dbAsset != null) {
//			dbAsset.setAssetName(masterAsset.getAssetName());
//			dbAsset.setAssetNo(masterAsset.getAssetNo());
//			dbAsset.setAssetType(masterAsset.getAssetType());
//			dbAsset.setAssetUser(masterAsset.getAssetUser());
//			dbAsset.setDiskType(masterAsset.getDiskType());
//			dbAsset.setOperatingSystem(masterAsset.getOperatingSystem());
//			dbAsset.setProcessor(masterAsset.getProcessor());
//			dbAsset.setRam(masterAsset.getRam());
//			dbAsset.setStatus(masterAsset.getStatus());
//			dbAsset.setWarrenty(masterAsset.getWarrenty());
//			dbAsset.setPurchesDate(masterAsset.getPurchesDate());
//			dbAsset.setWarrentyDate(masterAsset.getWarrentyDate());
//
//			return repo.save(dbAsset).getAssetId() + " Updated Successfully";
//		}
//
//		return masterAsset.getAssetId() + " Not Updated ";
//	}
//
//	@Override
//	public List<MasterAsset> findAllMasterAsset() {
//		return (List<MasterAsset>) repo.findAll();
//	}
}
