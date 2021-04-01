package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.AutoDao;
import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("autoService")
@Transactional
public class AutoServiceImpl implements AutoService{

	@Autowired
	private AutoDao dao;
	
	public Auto findById(int id) {
		return dao.findById(id);
	}

	public Auto findByTarga(String targa) {
		Auto auto = dao.findByTarga(targa);
		return auto;
	}

	public void saveAuto(Auto auto) {
		dao.save(auto);
	}

	public void updateAuto(Auto auto) {
		Auto entity = dao.findById(auto.getId());
		if(entity!=null){
			entity.setAnnoImmatricolazione(auto.getAnnoImmatricolazione());
			entity.setCasaCostruttrice(auto.getCasaCostruttrice());
			entity.setModello(auto.getModello());
			entity.setTipologia(auto.getTipologia());
			entity.setTarga(auto.getTarga());

		}
	}

	
	public void deleteAutoById(int id) {
		dao.deleteById(id);
	}

	public List<Auto> findAllAutos() {
		return dao.findAllAutos();
	}

	@Override
	public List<Auto> research(ResearchForm researchForm) {
		switch (researchForm.getField()){
			case "Anno di immatricolazione":researchForm.setField("annoImmatricolazione");break;
			case "Casa Costruttrice":researchForm.setField("casaCostruttrice");break;
			case "Targa":researchForm.setField("targa");break;
			case "Modello":researchForm.setField("modello");break;
			case "Tipologia":researchForm.setField("tipologia");break;
		}
		return dao.research(researchForm);
	}


}
