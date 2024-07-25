package com.adt.hrms.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.adt.hrms.model.MasterAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.repository.AVTechnologyRepo;
import com.adt.hrms.service.AVTechnologyService;

@Service
public class AVTechnologyImpl implements AVTechnologyService {

	@Autowired
	AVTechnologyRepo avTechnologyRepo;

	@Override
	public String saveTechnology(AVTechnology tc) {
		AVTechnology tech = avTechnologyRepo.findByDescription(tc.getDescription());
		if (tech != null) {
			return "Technology with description '" + tc.getDescription() + "' already exists";
		}
		AVTechnology savedTech = avTechnologyRepo.save(tc);
		return savedTech.getTechId() + " Technology is Saved";
	}

	@Override
	public List<AVTechnology> getAllTechnology() {
		List<AVTechnology> tech = avTechnologyRepo.findAll();
		return tech;
	}

	@Override
	public AVTechnology getTechnology(Integer techId) {
		Optional<AVTechnology> opt = avTechnologyRepo.findById(techId);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public String updateAVTechnology(AVTechnology technology) {
		AVTechnology AVTechnology = avTechnologyRepo.findById(technology.getTechId()).orElse(null);
		if (AVTechnology == null) {
			return "Technology not found";
		}
		AVTechnology.setDescription(technology.getDescription());
		avTechnologyRepo.save(AVTechnology);
		return "Technology updated successfully";
	}

	@Override
	public String deleteAVTechnologyById(Integer techId) {
		AVTechnology technology = avTechnologyRepo.findById(techId).orElse(null);
		if (technology == null) {
			return "Technology not found";
		}
		avTechnologyRepo.delete(technology);
		return "Technology deleted successfully";
	}

	@Override
	public AVTechnology getAVTechnologyById(Integer id) {
		Optional<AVTechnology> getById = avTechnologyRepo.findById(id);
		if (getById.isPresent()) {
			return getById.get();
		} else {
			throw new NoSuchElementException("Technology with id " + id + " not found");
		}
	}

	@Override
	public List<AVTechnology> searchTechnologiesByName(String technologyName) {
		List<AVTechnology> technologyList = avTechnologyRepo.findAllTechnologiesByName(technologyName);
		return technologyList;
	}
}
