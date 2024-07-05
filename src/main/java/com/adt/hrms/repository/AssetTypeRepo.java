package com.adt.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetType;

@Repository
public interface AssetTypeRepo extends JpaRepository<AssetType, Integer> {

	@Query(value = "SELECT * FROM av_schema.asset_type where name=?1", nativeQuery = true)
	Optional<AssetType> findByAssetName(String assetName);

	@Query(value = "SELECT * FROM av_schema.asset_type where id=?1", nativeQuery = true)
	Optional<AssetType> findAssetByAssetTypeId(Integer assetTypeId);

}
