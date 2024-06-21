package com.adt.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetInfo;

@Repository
public interface AssetInfoRepo extends JpaRepository<AssetInfo, Integer> {

}
