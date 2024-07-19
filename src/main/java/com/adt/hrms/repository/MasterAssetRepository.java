package com.adt.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.MasterAsset;

@Repository
public interface MasterAssetRepository extends JpaRepository<MasterAsset, Integer> {

	@Query(value = "SELECT ma FROM MasterAsset ma WHERE lower(ma.assetUser) like lower(concat('%', :query,'%'))")
	List<MasterAsset> findByAssetUser(@Param("query") String query);

	@Query(value = "SELECT ma FROM MasterAsset ma WHERE ma.status like %:query%")
	List<MasterAsset> findByStatus(@Param("query") String query);

	@Query(value = "SELECT ma FROM MasterAsset ma WHERE ma.assetType like %:query%")
	List<MasterAsset> findByAssetType(@Param("query") String query);

	public MasterAsset findAssetById(Integer id);

}
