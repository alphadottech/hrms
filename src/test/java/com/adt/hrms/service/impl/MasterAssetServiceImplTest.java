package com.adt.hrms.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

//import com.adt.hrms.model.MasterAsset;
//import com.adt.hrms.repository.MasterAssetRepository;


@SpringBootTest(classes = { MasterAssetServiceImplTest.class })
public class MasterAssetServiceImplTest {

//	@Mock
//	MasterAssetRepository repo;

	@InjectMocks
	MasterAssetServiceImpl assetImpl;

//	public List<MasterAsset> assets;
//
//	public MasterAsset asset;
	private static final int id = 1;
	private static final String assetUser = "assetUser";
	private static final String assetStatus = "assetStatus";
	private static final String assetType = "assetType";

//	@Test
//	@Order(1)
//	public void test_saveMasterAsset() {
//		MasterAsset asset = new MasterAsset();
//		asset.setId(0);
//		when(repo.save(asset)).thenReturn(asset);
//		assertEquals(true, assetImpl.saveMasterAsset(asset));
//	}
//
//	@Test
//	@Order(2)
//	public void test_TakeAssetById() {
//		MasterAsset asset = givenMasterAsset();
//		when(repo.findById(id)).thenReturn(Optional.of(asset));
//		assertEquals(asset, assetImpl.TakeAssetById(id));
//	}
//
//	@Test 
//	@Order(3)
//	public void test_SearchByAssetUser() {
//		List<MasterAsset> asset = new ArrayList<MasterAsset>();
//		when(repo.findByAssetUser(assetUser)).thenReturn(asset);
//		assertEquals(asset, assetImpl.SearchByAssetUser(assetUser));
//	}
//
//	@Test 
//	@Order(4)
//	public void test_SearchByStatus() {
//		List<MasterAsset> asset = new ArrayList<MasterAsset>();
//		when(repo.findByStatus(assetStatus)).thenReturn(asset);
//		assertEquals(asset, assetImpl.SearchByStatus(assetStatus));
//	}
//
//	@Test 
//	@Order(5)
//	public void test_SearchByAssetType() {
//		List<MasterAsset> asset = new ArrayList<MasterAsset>();
//		when(repo.findByAssetType(assetType)).thenReturn(asset);
//		assertEquals(asset, assetImpl.SearchByAssetType(assetType));
//	}
//
//	@Test 
//	@Order(6)
//	public void test_updateMasterAssetById() {
//		MasterAsset asset = givenMasterAsset();
//		when(repo.findAssetById(asset.getId())).thenReturn(asset);
//		when(repo.save(asset)).thenReturn(asset);
//		assertEquals("null Updated Successfully", assetImpl.updateMasterAssetById(asset));
//	}
//	
//	@Test 
//	@Order(7)
//	public void test_findAllMasterAsset() {
//		List<MasterAsset> assets = new ArrayList<MasterAsset>();
//		assets.add(asset);
//		assets.add(asset);
//		when(repo.findAll()).thenReturn(assets); // mocking
//		assertEquals(2, assetImpl.findAllMasterAsset().size());
//	}
//
//	private MasterAsset givenMasterAsset() {
//		MasterAsset masterAsset = new MasterAsset();
//		masterAsset.setId(id);
//		masterAsset.setAssetUser(assetUser);
//		masterAsset.setStatus(assetStatus);
//		masterAsset.setAssetType(assetType);
//
//		return masterAsset;
//	}
}
