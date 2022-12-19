package com.alphadot.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.alphadot.model.AttendanceInfo;

public interface AttendanceInfoRepository extends MongoRepository<AttendanceInfo, Integer> {

}
