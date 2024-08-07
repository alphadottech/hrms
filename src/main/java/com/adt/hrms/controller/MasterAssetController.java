package com.adt.hrms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.AssetType;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.AssignAssetsDTO;
import com.adt.hrms.request.ResponseDTO;
import com.adt.hrms.service.MasterAssetService;
import com.adt.hrms.util.HttpStatusMapper;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/masterAsset")
public class MasterAssetController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MasterAssetService service;

	@PreAuthorize("@auth.allow('ADD_ASSET_TYPE')")
	@PostMapping(value = "/addAssetType")
	public ResponseEntity<ResponseDTO> addAssetType(@RequestBody AssetType assetType) {
		LOGGER.info("MasterAssetController: addAssetType info level log message");
		ResponseDTO responseDTO = service.addAssetType(assetType);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ASSET_TYPE_BY_ID')")
	@GetMapping(value = "/getAssetTypeById/{assetTypeId}")
	public ResponseEntity<Object> getAssetTypeById(@PathVariable Integer assetTypeId) {
		LOGGER.info("MasterAssetController:masterAsset:getAssetTypeById info level log message");
		ResponseDTO responseDTO = service.getAssetTypeById(assetTypeId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_TYPE')")
	@GetMapping(value = "/getAllAssetType")
	public ResponseEntity<Object> getAllAssetType() {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssetType info level log message");
		ResponseDTO responseDTO = service.getAllAssetType();
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('UPDATE_ASSET_TYPE_BY_ID')")
	@PutMapping(value = "/updateAssetTypeById/{assetTypeId}")
	public ResponseEntity<Object> updateAssetTypeById(@PathVariable Integer assetTypeId,
			@RequestBody AssetType assetType) {
		LOGGER.info("MasterAssetController:masterAsset:updateAssetTypeById info level log message");
		ResponseDTO responseDTO = service.updateAssetTypeById(assetTypeId, assetType);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('DELETE_ASSET_TYPE_BY_ID')")
	@DeleteMapping(value = "/deleteAssetTypeById/{assetTypeId}")
	public ResponseEntity<Object> deleteAssetTypeById(@PathVariable Integer assetTypeId) {
		LOGGER.info("MasterAssetController:masterAsset:deleteAssetTypeById info level log message");
		ResponseDTO responseDTO = service.deleteAssetTypeById(assetTypeId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('ADD_ASSET_ATTRIBUTE_BY_ASSET_TYPE_ID')")
	@PostMapping(value = "/addAssetAttributeByAssetTypeId/{assetTypeId}")
	public ResponseEntity<Object> addAssetAttributeByAssetTypeId(@PathVariable Integer assetTypeId,
			@RequestParam("assetAttributeName") String assetAttributeName) {
		LOGGER.info("MasterAssetController:masterAsset:addAssetAttributeByAssetTypeId info level log message");
		ResponseDTO responseDTO = service.addAssetAttributesByAssetTypeId(assetTypeId, assetAttributeName);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_ATTRIBUTES_BY_ASSET_TYPE_ID')")
	@GetMapping(value = "/getAllAssetAttributesByAssetTypeId/{assetTypeId}")
	public ResponseEntity<Object> getAllAssetAttributesByAssetTypeId(@PathVariable Integer assetTypeId) {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssetAttributesByAssetTypeId info level log message");
		ResponseDTO responseDTO = service.getAllAssetAttributesByAssetTypeId(assetTypeId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('UPDATE_ASSET_ATTRIBUTE_BY_ID')")
	@PutMapping(value = "/updateAssetAttributeById/{assetAttributeId}")
	public ResponseEntity<Object> updateAssetAttributeById(@PathVariable Integer assetAttributeId,
			@RequestParam("assetTypeId") Integer assetTypeId,
			@RequestParam("assetAttributeName") String assetAttributeName) {
		LOGGER.info("MasterAssetController:masterAsset:updateAssetAttributeById info level log message");
		ResponseDTO responseDTO = service.updateAssetAttributeById(assetAttributeId, assetTypeId, assetAttributeName);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('DELETE_ASSET_ATTRIBUTE_BY_ID')")
	@DeleteMapping(value = "/deleteAssetAttributeById/{assetAttributeId}")
	public ResponseEntity<Object> deleteAssetAttributeById(@PathVariable Integer assetAttributeId) {
		LOGGER.info("MasterAssetController:masterAsset:deleteAssetAttributeById info level log message");
		ResponseDTO responseDTO = service.deleteAssetAttributeById(assetAttributeId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('SAVE_ASSET_INFO')")
	@PostMapping(value = "/saveAssetInfo")
	public ResponseEntity<Object> saveAssetInfo(@RequestBody AssetDTO assetDTO) {
		LOGGER.info("MasterAssetController:masterAsset:saveAssetInfo info level log message");
		ResponseDTO responseDTO = service.saveAssetInfo(assetDTO);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_INFO_BY_ASSET_TYPE_ID_AND_PAGINATION')")
	@GetMapping(value = "/getAllAssetInfoByAssetTypeIdAndPagination/{assetTypeId}")
	public ResponseEntity<Object> getAllAssetInfoByAssetTypeIdAndPagination(@PathVariable Integer assetTypeId,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size) {
		LOGGER.info(
				"MasterAssetController:masterAsset:getAllAssetInfoByAssetTypeIdAndPagination info level log message");
		ResponseDTO responseDTO = service.getAllAssetInfoByAssetTypeIdAndPagination(assetTypeId, page, size);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('UPDATE_ASSET_ATTRIBUTE_MAPPING_BY_ASSET_ID')")
	@PutMapping(value = "/updateAssetAttributeMappingByAssetId")
	public ResponseEntity<Object> updateAssetAttributeMappingByAssetId(@RequestBody AssetDTO assetDTO) {
		LOGGER.info("MasterAssetController:masterAsset:updateAssetAttributeMappingByAssetId info level log message");
		ResponseDTO responseDTO = service.updateAssetAttributeMappingByAssetId(assetDTO);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('DELETE_ASSET_INFO_BY_ID')")
	@DeleteMapping(value = "/deleteAssetInfoById/{assetId}")
	public ResponseEntity<Object> deleteAssetInfoById(@PathVariable Integer assetId) {
		LOGGER.info("MasterAssetController:masterAsset:deleteAssetInfoById info level log message");
		ResponseDTO responseDTO = service.deleteAssetInfoById(assetId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ASSET_INFO_BY_ASSET_ADT_ID')")
	@GetMapping(value = "/getAssetInfoByAssetAdtId")
	public ResponseEntity<Object> getAssetInfoByAssetAdtId(@RequestParam(value = "assetAdtId") String assetAdtId) {
		LOGGER.info("MasterAssetController:masterAsset:getAssetInfoByAssetAdtId info level log message");
		ResponseDTO responseDTO = service.getAssetInfoByAssetAdtId(assetAdtId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSIGNED_ASSETS_BY_EMPLOYEE_ADT_ID')")
	@GetMapping("/getAllAssignedAssetsByEmpADTId")
	public ResponseEntity<Object> getAllAssignedAssetsByEmpADTId(@RequestParam(value = "empADTId") String empADTId) {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssignedAssetsByEmpADTId info level log message");
		ResponseDTO responseDTO = service.getAllAssignedAssetsByEmpADTId(empADTId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('SEARCH_EMPLOYEE_DETAILS')")
	@GetMapping("/searchEmployeeDetails")
	public ResponseEntity<Object> searchEmployeeDetails(
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "empAdtId", required = false) String empAdtId,
			@RequestParam(value = "firstLetter", required = false) String firstLetter,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		LOGGER.info("MasterAssetController:masterAsset:searchEmployeeDetails info level log message");
		ResponseDTO responseDTO = service.searchEmployeeDetails(firstName, lastName, empAdtId, firstLetter, page, size);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('ASSIGN_ALL_ASSETS_TO_EMPLOYEE')")
	@PutMapping(value = "/assignAllAssetsToEmp")
	public ResponseEntity<Object> assignAllAssetsToEmp(@RequestBody AssignAssetsDTO assignAssetsDTO) {
		LOGGER.info("MasterAssetController:masterAsset:assignAllAssetsToEmp info level log message");
		ResponseDTO responseDTO = service.assignAllAssetsToEmp(assignAssetsDTO);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

}
