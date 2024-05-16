package com.adt.hrms.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriveDetailsRepository extends JpaRepository<DriveDetails, Integer> {
    Optional<DriveDetails> findByStatus(boolean status);
}