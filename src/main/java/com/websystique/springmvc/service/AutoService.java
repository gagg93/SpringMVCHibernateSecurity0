package com.websystique.springmvc.service;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.User;

import java.util.List;


public interface AutoService {
	
	Auto findById(int id);

	Auto findByTarga(String targa);
	
	void saveAuto(Auto auto);
	
	void updateAuto(Auto auto);
	
	void deleteAutoById(int id);

	List<Auto> findAllAutos();


    List<Auto> research(ResearchForm researchForm);
}