package com.alphadot.repository;

import java.io.Serializable;


import org.springframework.data.jpa.repository.JpaRepository;


import com.alphadot.model.EmployeeStatus;

public interface EmployeeStatusRepo extends JpaRepository<EmployeeStatus, Serializable> {
	

}
