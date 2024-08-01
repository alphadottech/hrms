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

import com.adt.hrms.model.AssetEmployeeMapping;
import com.adt.hrms.model.AssetType;
import com.adt.hrms.request.AssetDTO;
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

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_TYPE')")
	@GetMapping(value = "/getAllAssetType")
	public ResponseEntity<Object> getAllAssetType() {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssetType info level log message");
		ResponseDTO responseDTO = service.getAllAssetType();
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

	@PreAuthorize("@auth.allow('SAVE_ASSET_INFO')")
	@PostMapping(value = "/saveAssetInfo")
	public ResponseEntity<Object> saveAssetInfo(@RequestBody AssetDTO assetDTO) {
		LOGGER.info("MasterAssetController:masterAsset:saveAssetInfo info level log message");
		ResponseDTO responseDTO = service.saveAssetInfo(assetDTO);
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

	@PreAuthorize("@auth.allow('GET_ALL_ASSIGNED_ASSETS_TO_EMPLOYEE_BY_ID')")
	@GetMapping("/getAllAssignedAssetsToEmpById")
	public ResponseEntity<Object> getAllAssignedAssetsToEmpById(@RequestParam(value = "empId") String empId) {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssignedAssetsToEmpById info level log message");
		ResponseDTO responseDTO = service.getAllAssignedAssetsToEmpById(empId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('GET_ASSET_INFO_BY_ID')")
	@GetMapping(value = "/getAssetInfoById/{assetId}")
	public ResponseEntity<Object> getAssetInfoById(@PathVariable Integer assetId) {
		LOGGER.info("MasterAssetController:masterAsset:getAssetInfoById info level log message");
		ResponseDTO responseDTO = service.getAssetInfoById(assetId);
		HttpStatus status = HttpStatusMapper.mapToHttpStatus(responseDTO.getStatus());
		return new ResponseEntity<>(responseDTO, status);
	}

	@PreAuthorize("@auth.allow('ASSIGN_ASSET_TO_EMPLOYEE')")
	@PostMapping(value = "/assignAssetToEmp")
	public ResponseEntity<Object> assignAssetToEmp(@RequestBody AssetEmployeeMapping assetEmployeeMapping) {
		LOGGER.info("MasterAssetController:masterAsset:assignAssetToEmp info level log message");
		ResponseDTO responseDTO = service.assignAssetToEmp(assetEmployeeMapping);
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

//@PreAuthorize("@auth.allow('SAVE_ASSET_DETAILS_WITH_ATTRIBUTES')")
//@PostMapping(value = "/saveAssetDetailsWithAttributes")
//public ResponseEntity<Object> saveAssetDetailsWithAttributes(@RequestBody CreateAssetDTO createAssetDTO) {
//	LOGGER.info("MasterAssetController:masterAsset:saveAssetDetailsWithAttributes info level log message");
//	ResponseDTO responseDTO = service.saveAssetDetailsWithAttributes(createAssetDTO);
//	if (responseDTO.getStatus().equalsIgnoreCase("Success")) {
//		return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
//	} else {
//		return new ResponseEntity<Object>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//}
//
//	@PreAuthorize("@auth.allow('SAVE_NEW_MASTER_ASSET')")
//	@PostMapping("/insertAssets")
//	public ResponseEntity<String> insertAsset(@RequestBody MasterAsset asset) {
//		LOGGER.info("MasterAssetController:masterAsset:saveMasterAsset info level log message");
//		String result = null;
//		if (service.saveMasterAsset(asset)) {
//			result = "Asset saved successfully !";
//		} else {
//			result = "Asset Not saved !";
//		}
//		return new ResponseEntity<String>(result, HttpStatus.OK);
//	}
//
//	@PreAuthorize("@auth.allow('GET_MASTER_ASSET_DETAILS_BY_ID')")
//	@GetMapping("/GetAssetById/{id}")
//	public ResponseEntity<MasterAsset> GetAssetById(@PathVariable Integer id) {
//		LOGGER.info("MasterAssetController:masterAsset:TakeAssetById info level log message");
//		return ResponseEntity.ok(service.TakeAssetById(id));
//	}
//
//	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_DETAILS_BY_USER')")
//	@GetMapping("/searchByAssetUser")
//	public ResponseEntity<List<MasterAsset>> SearchAssetUser(@RequestParam("query") String query) {
//		LOGGER.info("MasterAssetController:masterAsset:SearchByAssetUser info level log message");
//		return ResponseEntity.ok(service.SearchByAssetUser(query));
//	}
//
//	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_BY_STATUS')")
//	@GetMapping("/searchByStatus")
//	public ResponseEntity<List<MasterAsset>> SearchByStatus(@RequestParam("query") String query) {
//		LOGGER.info("MasterAssetController:masterAsset:SearchByStatus info level log message");
//		return ResponseEntity.ok(service.SearchByStatus(query));
//	}
//
//	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_BY_ASSET_TYPE')")
//	@GetMapping("/searchByAssetType")
//	public ResponseEntity<List<MasterAsset>> SearchByAssetType(@RequestParam("query") String query) {
//		LOGGER.info("MasterAssetController:masterAsset:SearchByAssetType info level log message");
//		return ResponseEntity.ok(service.SearchByAssetType(query));
//	}
//
//	@PreAuthorize("@auth.allow('UPDATE_MASTER_ASSET_BY_ASSET_ID')")
//	@CrossOrigin(origins = "*")
//	@PutMapping("/updateMasterAssetbyid")
//	public ResponseEntity<String> updateMasterAssetbyid(@RequestBody MasterAsset asset) {
//		LOGGER.info("MasterAssetController:masterAsset:updateMasterAssetById info level log message");
//		return new ResponseEntity<String>(service.updateMasterAssetById(asset), HttpStatus.OK);
//	}
//
//	@PreAuthorize("@auth.allow('GET_ALL_MASTER_ASSET_DETAILS')")
//	@GetMapping("/getAllMasterAsset")
//	public ResponseEntity<List<MasterAsset>> findAllAssets() {
//		LOGGER.info("MasterAssetController:masterAsset:findAllMasterAsset info level log message");
//		return new ResponseEntity<List<MasterAsset>>(service.findAllMasterAsset(), HttpStatus.OK);
//	}

}
