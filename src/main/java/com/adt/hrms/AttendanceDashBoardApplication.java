package com.adt.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.adt.hrms.service.impl.AVHashMapServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot Main app
 *
 * @author hp
 *
 */
@SpringBootApplication
@EnableScheduling
public class AttendanceDashBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceDashBoardApplication.class, args);
	}

	@Bean
	public AVHashMapServiceImpl getAllAVHashMapServiceDetails() {
		return new AVHashMapServiceImpl();
	}

}
