package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetAttributeMapping;

@Repository
public interface AssetAttributeMappingRepo extends JpaRepository<AssetAttributeMapping, Integer> {

	@Query(value = "SELECT * FROM employee_schema.asset_attribute_mapping where asset_id=?1", nativeQuery = true)
	Optional<List<AssetAttributeMapping>> findAssetAttributeMappingListByAssetInfoId(Integer id);

	@Query(value = "SELECT * FROM employee_schema.asset_attribute_mapping where asset_id=?1 and asset_attribute_id=?2", nativeQuery = true)
	Optional<AssetAttributeMapping> findByAssetIdAndAssetAttributeId(Integer assetId, Integer assetAttributeId);

}
