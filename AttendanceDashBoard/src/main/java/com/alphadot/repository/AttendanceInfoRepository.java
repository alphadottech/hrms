package com.alphadot.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alphadot.model.AttendanceInfo;
@Repository
public interface AttendanceInfoRepository extends MongoRepository<AttendanceInfo, Integer> {

}
