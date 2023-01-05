package com.adt.hrms.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.hrms.model.Technology;

public interface TechnologyRepo extends JpaRepository<Technology, Serializable>{

}
