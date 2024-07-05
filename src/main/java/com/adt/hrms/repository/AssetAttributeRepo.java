package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetAttribute;

@Repository
public interface AssetAttributeRepo extends JpaRepository<AssetAttribute, Integer> {

	@Query(value = "SELECT * FROM av_schema.asset_attribute where asset_type_id=?1", nativeQuery = true)
	List<AssetAttribute> findAllAssetAttributesByAssetTypeId(Integer assetTypeId);

	@Query(value = "SELECT * FROM av_schema.asset_attribute where name=?1 and asset_type_id=?2", nativeQuery = true)
	Optional<AssetAttribute> findByAssetAttributeName(String name, Integer asset_type_id);

	@Query(value = "DELETE FROM av_schema.asset_attribute WHERE asset_type_id=?1", nativeQuery = true)
	void deleteByAssetTypeId(Integer assetTypeId);

	@Query(value = "SELECT * FROM av_schema.asset_attribute where id=?1 and asset_type_id=?2", nativeQuery = true)
	Optional<AssetAttribute> findAssetAttributes(Integer assetAttributeId, Integer assetTypeId);

	@Query(value = "SELECT * FROM av_schema.asset_attribute where asset_type_id=?1", nativeQuery = true)
	Optional<List<AssetAttribute>> findAssetAttributeByAssetTypeId(Integer assetTypeId);

}
