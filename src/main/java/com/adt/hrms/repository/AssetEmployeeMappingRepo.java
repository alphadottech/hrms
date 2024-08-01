package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetEmployeeMapping;

@Repository
public interface AssetEmployeeMappingRepo extends JpaRepository<AssetEmployeeMapping, Integer> {

	@Query(value = "SELECT * FROM employee_schema.asset_employee_mapping where asset_id=?1", nativeQuery = true)
	Optional<AssetEmployeeMapping> findByAssetId(Integer assetId);

	@Query(value = "SELECT * FROM employee_schema.asset_employee_mapping where emp_id=?1", nativeQuery = true)
	Optional<List<AssetEmployeeMapping>> findAssetsByEmpId(Integer empId);

}
