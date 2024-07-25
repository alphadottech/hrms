package com.adt.hrms.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adt.hrms.model.AVTechnology;

public interface AVTechnologyRepo extends JpaRepository<AVTechnology, Serializable> {

	AVTechnology findByDescription(String description);

	@Query(value = "SELECT * FROM av_schema.technology WHERE LOWER(description) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
	List<AVTechnology> findAllTechnologiesByName(String technologyName);

}
