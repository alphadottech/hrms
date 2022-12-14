package com.alphadot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.alphadot.model.Employee;
@Repository
public interface EmployeeRepo extends MongoRepository<Employee, Integer> {

}
