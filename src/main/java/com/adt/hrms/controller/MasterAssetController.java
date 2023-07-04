package com.adt.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private MasterAssetService service;
	
	@PostMapping("/insertAssets")
	public ResponseEntity<String> insertAsset(@RequestBody MasterAsset asset) {
	     String result = null;
		if(service.saveMasterAsset(asset)) {
			result =  "Asset saved successfully !";
		}else{
			result =  "Asset Not saved !";
		}
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@GetMapping("/GetAssetById/{id}")
	public ResponseEntity<MasterAsset> GetAssetById(@PathVariable Integer id){
	return ResponseEntity.ok(service.TakeAssetById(id));
}
	
	//JyotiPancholi - Jira no ->  HRMS-83(START)
	
	@GetMapping("/searchByAssetUser")
	public ResponseEntity<List<MasterAsset>> SearchAssetUser(@RequestParam("query") String query) {
		return ResponseEntity.ok(service.SearchByAssetUser(query));
	}
	
	@GetMapping("/searchByStatus")
	public ResponseEntity<List<MasterAsset>> SearchByStatus(@RequestParam("query") String query) {
		return ResponseEntity.ok(service.SearchByStatus(query));
	}
	
	@GetMapping("/searchByAssetType")
	public ResponseEntity<List<MasterAsset>> SearchByAssetType(@RequestParam("query") String query) {
		return ResponseEntity.ok(service.SearchByAssetType(query));
	}
	//JyotiPancholi - Jira no ->  HRMS-83(END)
	
	//RitikaBhawsar - Jira no ->  HRMS-63(START)
	@PutMapping("/updateMasterAssetbyid")
	public ResponseEntity<String> updateMasterAssetbyid( @RequestBody MasterAsset asset) 
	{
		// service.updateMasterAsset(asset);
		//LOGGER.info("MasterAssetService:masterAsset:updateMasterAssetbyid info level log message");

		return new ResponseEntity<String>(service.updateMasterAssetById(asset), HttpStatus.OK);
	}
	
	@GetMapping("/getAllMasterAsset")
	public ResponseEntity<List<MasterAsset>> findAllAssets()
	{
		//LOGGER.info("MasterAssetService:masterAsset:findAllAssets info level log message");

		return new ResponseEntity<List<MasterAsset>>(service.findAllMasterAsset(), HttpStatus.OK);
	}
	//RitikaBhawsar - Jira no ->  HRMS-63(END)


}
//JyotiPancholi - Jira no ->  HRMS-63(END)
