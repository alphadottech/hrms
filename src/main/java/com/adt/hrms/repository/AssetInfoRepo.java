package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetInfo;

@Repository
public interface AssetInfoRepo extends JpaRepository<AssetInfo, Integer> {

	@Query(value = "SELECT * FROM employee_schema.asset where asset_type_id=?1", nativeQuery = true)
	Optional<List<AssetInfo>> findAssetInfoListByAssetTypeId(Integer assetTypeId);

	@Query(value = "SELECT * FROM employee_schema.asset where id=?1", nativeQuery = true)
	Optional<AssetInfo> findAssetByAssetId(Integer assetId);

	@Query(value = "DELETE FROM employee_schema.asset WHERE asset_type_id=?1", nativeQuery = true)
	void deleteByAssetTypeId(Integer assetTypeId);

	@Query(value = "SELECT * FROM employee_schema.asset WHERE asset_type_id = ?1", nativeQuery = true)
	Page<AssetInfo> findListByAssetTypeIdWithPagination(Integer assetTypeId, Pageable pageable);

}
