package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.AutoDao;
import com.websystique.springmvc.dao.PrenotazioneDao;
import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.dto.PrenotazioneDto;
import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.Prenotazione;
import com.websystique.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service("prenotazioneService")
@Transactional
public class PrenotazioneServiceImpl implements PrenotazioneService{

	@Autowired
	private PrenotazioneDao dao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AutoDao autoDao;
	
	public PrenotazioneDto findById(int id) {
		return new PrenotazioneDto(dao.findById(id));
	}

	public List<PrenotazioneDto> findByUser(int id) {
		User user=userDao.findById(id);
		List<Prenotazione> prenotazione = dao.findByUser(user);
		List<PrenotazioneDto> prenotazioneDtos=new ArrayList<>();
		for (Prenotazione var :
				prenotazione) {
			prenotazioneDtos.add(new PrenotazioneDto(var));
		}
		return prenotazioneDtos;
	}

	public List<PrenotazioneDto> findByAuto(Auto auto) {
		List<Prenotazione> prenotazione = dao.findByAuto(auto);
		List<PrenotazioneDto> prenotazioneDtos=new ArrayList<>();
		for (Prenotazione var :
				prenotazione) {
			prenotazioneDtos.add(new PrenotazioneDto(var));
		}
		return prenotazioneDtos;
	}

	public void savePrenotazione(PrenotazioneDto prenotazione) {
		User user=userDao.findByUsername(prenotazione.getUsername());
		Auto auto=autoDao.findByTarga(prenotazione.getTarga());
		dao.save(new Prenotazione(prenotazione.getDataDiInizio(),prenotazione.getDataDiFine(),auto,user));
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updatePrenotazione(PrenotazioneDto prenotazione) {
		Prenotazione entity = dao.findById(prenotazione.getId());
		if(entity!=null){
			entity.setApprovata(prenotazione.getApprovata());
			entity.setAuto(autoDao.findByTarga(prenotazione.getTarga()));
			entity.setUser(userDao.findByUsername(prenotazione.getUsername()));
			entity.setDataDiInizio(prenotazione.getDataDiInizio());
			entity.setDataDiFine(prenotazione.getDataDiFine());
		}
	}

	
	public void deletePrenotazioneById(int id) {
		dao.deleteById(id);
	}

	public List<PrenotazioneDto> findAllPrenotaziones() {
		List<Prenotazione> prenotazione = dao.findAllPrenotaziones();
		List<PrenotazioneDto> prenotazioneDtos=new ArrayList<>();
		for (Prenotazione var :
				prenotazione) {
			prenotazioneDtos.add(new PrenotazioneDto(var));
		}
		return prenotazioneDtos;
	}


	public List<PrenotazioneDto> research(ResearchForm researchForm) throws ParseException {
		switch(researchForm.getField()){
			case "Username":researchForm.setField("username");break;
			case "Targa":researchForm.setField("targa");break;
			case "Data di inizio":researchForm.setField("dataDiInizio");break;
			case "Data di fine":researchForm.setField("dataDiFine");break;
		}
		List<PrenotazioneDto> result=new ArrayList<>();
		List<Prenotazione> p=dao.research(researchForm);
		for (Prenotazione var :
				p) {
			result.add(new PrenotazioneDto(var));
		}
		return result;
	}
	
}
