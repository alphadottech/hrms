package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.request.AssetDTO;
import com.adt.hrms.request.CreateAssetDTO;
import com.adt.hrms.request.ResponseDTO;
import com.adt.hrms.service.MasterAssetService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/masterAsset")
public class MasterAssetController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MasterAssetService service;

	@PreAuthorize("@auth.allow('SAVE_NEW_MASTER_ASSET')")
	@PostMapping("/insertAssets")
	public ResponseEntity<String> insertAsset(@RequestBody MasterAsset asset) {
		LOGGER.info("MasterAssetController:masterAsset:saveMasterAsset info level log message");
		String result = null;
		if (service.saveMasterAsset(asset)) {
			result = "Asset saved successfully !";
		} else {
			result = "Asset Not saved !";
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GET_MASTER_ASSET_DETAILS_BY_ID')")
	@GetMapping("/GetAssetById/{id}")
	public ResponseEntity<MasterAsset> GetAssetById(@PathVariable Integer id) {
		LOGGER.info("MasterAssetController:masterAsset:TakeAssetById info level log message");
		return ResponseEntity.ok(service.TakeAssetById(id));
	}

	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_DETAILS_BY_USER')")
	@GetMapping("/searchByAssetUser")
	public ResponseEntity<List<MasterAsset>> SearchAssetUser(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetController:masterAsset:SearchByAssetUser info level log message");
		return ResponseEntity.ok(service.SearchByAssetUser(query));
	}

	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_BY_STATUS')")
	@GetMapping("/searchByStatus")
	public ResponseEntity<List<MasterAsset>> SearchByStatus(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetController:masterAsset:SearchByStatus info level log message");
		return ResponseEntity.ok(service.SearchByStatus(query));
	}

	@PreAuthorize("@auth.allow('SEARCH_MASTER_ASSET_BY_ASSET_TYPE')")
	@GetMapping("/searchByAssetType")
	public ResponseEntity<List<MasterAsset>> SearchByAssetType(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetController:masterAsset:SearchByAssetType info level log message");
		return ResponseEntity.ok(service.SearchByAssetType(query));
	}

	@PreAuthorize("@auth.allow('UPDATE_MASTER_ASSET_BY_ASSET_ID')")
	@CrossOrigin(origins = "*")
	@PutMapping("/updateMasterAssetbyid")
	public ResponseEntity<String> updateMasterAssetbyid(@RequestBody MasterAsset asset) {
		LOGGER.info("MasterAssetController:masterAsset:updateMasterAssetById info level log message");
		return new ResponseEntity<String>(service.updateMasterAssetById(asset), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GET_ALL_MASTER_ASSET_DETAILS')")
	@GetMapping("/getAllMasterAsset")
	public ResponseEntity<List<MasterAsset>> findAllAssets() {
		LOGGER.info("MasterAssetController:masterAsset:findAllMasterAsset info level log message");
		return new ResponseEntity<List<MasterAsset>>(service.findAllMasterAsset(), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_TYPE')")
	@GetMapping(value = "/getAllAssetType")
	public ResponseEntity<Object> getAllAssetType() {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssetType info level log message");
		ResponseDTO responseDTO = service.getAllAssetType();
		if (responseDTO.getStatus().equalsIgnoreCase("Success")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		} else if (responseDTO.getStatus().equalsIgnoreCase("NotFound")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("@auth.allow('GET_ALL_ASSET_ATTRIBUTES_BY_ASSET_TYPE_ID')")
	@GetMapping(value = "/getAllAssetAttributesByAssetTypeId/{assetTypeId}")
	public ResponseEntity<Object> getAllAssetAttributesByAssetTypeId(@PathVariable Integer assetTypeId) {
		LOGGER.info("MasterAssetController:masterAsset:getAllAssetAttributesByAssetTypeId info level log message");
		ResponseDTO responseDTO = service.getAllAssetAttributesByAssetTypeId(assetTypeId);
		if (responseDTO.getStatus().equalsIgnoreCase("Success")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		} else if (responseDTO.getStatus().equalsIgnoreCase("NotFound")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("@auth.allow('SAVE_ASSET_DETAILS_WITH_ATTRIBUTES')")
	@PostMapping(value = "/saveAssetDetailsWithAttributes")
	public ResponseEntity<Object> saveAssetDetailsWithAttributes(@RequestBody CreateAssetDTO createAssetDTO) {
		LOGGER.info("MasterAssetController:masterAsset:saveAssetDetailsWithAttributes info level log message");
		ResponseDTO responseDTO = service.saveAssetDetailsWithAttributes(createAssetDTO);
		if (responseDTO.getStatus().equalsIgnoreCase("Success")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("@auth.allow('SAVE_ASSET_INFO')")
	@PostMapping(value = "/saveAssetInfo")
	public ResponseEntity<Object> saveAssetInfo(@RequestBody AssetDTO assetDTO) {
		LOGGER.info("MasterAssetController:masterAsset:saveAsset info level log message");
		ResponseDTO responseDTO = service.saveAssetInfo(assetDTO);
		if (responseDTO.getStatus().equalsIgnoreCase("Success")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		} else if (responseDTO.getStatus().equalsIgnoreCase("NotSaved")) {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
