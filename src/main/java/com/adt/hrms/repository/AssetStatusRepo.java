package com.adt.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetStatus;

@Repository
public interface AssetStatusRepo extends JpaRepository<AssetStatus, Integer> {

	@Query(value = "SELECT * FROM av_schema.asset_status where asset_status=?1", nativeQuery = true)
	Optional<AssetStatus> findAssetStatus(String assetStatus);

}
