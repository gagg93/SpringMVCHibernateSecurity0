package com.websystique.springmvc.dao;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;

import java.util.List;


public interface AutoDao {

	Auto findById(int id);

	Auto findByTarga(String targa);
	
	void save(Auto auto);
	
	void deleteById(int id);
	
	List<Auto> findAllAutos();

    List<Auto> research(ResearchForm researchForm);
}

