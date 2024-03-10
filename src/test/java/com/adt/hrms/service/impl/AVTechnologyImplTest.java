package com.adt.hrms.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.repository.AVTechnologyRepo;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AVTechnologyImplTest {
	private static final int TechId = 1;
	private static final String Description = "Mock";

	@InjectMocks
	private AVTechnologyImpl avTsrevice;

	@Mock
	AVTechnologyRepo avTechnologyRepo;

	@Test
	void saveTechnology() {
		AVTechnology avt = givenAVTechnology();
		avt.setTechId(0);
		when(avTechnologyRepo.save(avt)).thenReturn(avt);
		assertEquals(avt.getTechId() + " Technology is Saved", avTsrevice.saveTechnology(avt));
	}

	@Test
	void getAllTechnology() {
		AVTechnology avt = givenAVTechnology();
		List<AVTechnology> technologyList = Collections.singletonList(avt);
		when(avTechnologyRepo.findAll()).thenReturn(technologyList);
		assertEquals(technologyList, avTsrevice.getAllTechnology());
	}

	@Test
	void getTechnology() {
		AVTechnology avt = new AVTechnology();
		when(avTechnologyRepo.findById(TechId)).thenReturn(Optional.of(avt));
		assertEquals(avt, avTsrevice.getTechnology(TechId));
	}

	@Test
	void getNullTechnology() {
		AVTechnology avt = new AVTechnology();
		when(avTechnologyRepo.findById(TechId)).thenReturn(Optional.empty());
		assertNull(avTsrevice.getTechnology(TechId));
	}

	private AVTechnology givenAVTechnology() {
		AVTechnology avt = new AVTechnology();
		avt.setTechId(TechId);
		avt.setDescription(Description);
		return avt;
	}
}