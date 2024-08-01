package com.adt.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.AssetStatus;

@Repository
public interface AssetStatusRepo extends JpaRepository<AssetStatus, Integer> {

}
