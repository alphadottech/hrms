package com.adt.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetAttributeMapping;

@Repository
public interface AssetAttributeMappingRepo extends JpaRepository<AssetAttributeMapping, Integer> {

}
