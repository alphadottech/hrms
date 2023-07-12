package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.MasterAsset;
import com.adt.hrms.service.MasterAssetService;

//JyotiPancholi - Jira no ->  HRMS-63(START)

@RestController
@RequestMapping("/masterAsset")
public class MasterAssetController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MasterAssetService service;
	
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PostMapping("/insertAssets")
	public ResponseEntity<String> insertAsset(@RequestBody MasterAsset asset) {
		LOGGER.info("MasterAssetService:masterAsset:saveMasterAsset info level log message");
	     String result = null;
		if(service.saveMasterAsset(asset)) {
			result =  "Asset saved successfully !";
		}else{
			result =  "Asset Not saved !";
		}
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/GetAssetById/{id}")
	public ResponseEntity<MasterAsset> GetAssetById(@PathVariable Integer id){
		LOGGER.info("MasterAssetService:masterAsset:TakeAssetById info level log message");
	return ResponseEntity.ok(service.TakeAssetById(id));
}
	
	//JyotiPancholi - Jira no ->  HRMS-83(START)
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/searchByAssetUser")
	public ResponseEntity<List<MasterAsset>> SearchAssetUser(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetService:masterAsset:SearchByAssetUser info level log message");
		return ResponseEntity.ok(service.SearchByAssetUser(query));
	}
	
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/searchByStatus")
	public ResponseEntity<List<MasterAsset>> SearchByStatus(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetService:masterAsset:SearchByStatus info level log message");
		return ResponseEntity.ok(service.SearchByStatus(query));
	}
	
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/searchByAssetType")
	public ResponseEntity<List<MasterAsset>> SearchByAssetType(@RequestParam("query") String query) {
		LOGGER.info("MasterAssetService:masterAsset:SearchByAssetType info level log message");
		return ResponseEntity.ok(service.SearchByAssetType(query));
	}
	//JyotiPancholi - Jira no ->  HRMS-83(END)
	
	//RitikaBhawsar - Jira no ->  HRMS-63(START)
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PutMapping("/updateMasterAssetbyid")
	public ResponseEntity<String> updateMasterAssetbyid( @RequestBody MasterAsset asset) 
	{
		// service.updateMasterAsset(asset);
		//LOGGER.info("MasterAssetService:masterAsset:updateMasterAssetbyid info level log message");
		LOGGER.info("MasterAssetService:masterAsset:updateMasterAssetById info level log message");
		return new ResponseEntity<String>(service.updateMasterAssetById(asset), HttpStatus.OK);
	}
	
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getAllMasterAsset")
	public ResponseEntity<List<MasterAsset>> findAllAssets()
	{
		//LOGGER.info("MasterAssetService:masterAsset:findAllAssets info level log message");
		LOGGER.info("MasterAssetService:masterAsset:findAllMasterAsset info level log message");
		return new ResponseEntity<List<MasterAsset>>(service.findAllMasterAsset(), HttpStatus.OK);
	}
	//RitikaBhawsar - Jira no ->  HRMS-63(END)


}
//JyotiPancholi - Jira no ->  HRMS-63(END)
