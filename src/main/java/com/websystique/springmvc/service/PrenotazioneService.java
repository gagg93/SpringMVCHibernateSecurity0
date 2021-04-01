package com.websystique.springmvc.service;

import com.websystique.springmvc.dto.PrenotazioneDto;
import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.Prenotazione;
import com.websystique.springmvc.model.User;

import java.text.ParseException;
import java.util.List;


public interface PrenotazioneService {
	
	PrenotazioneDto findById(int id);

	List<PrenotazioneDto> findByUser(int id);

	List<PrenotazioneDto> findByAuto(Auto auto);
	
	void savePrenotazione(PrenotazioneDto prenotazione);
	
	void updatePrenotazione(PrenotazioneDto prenotazione);
	
	void deletePrenotazioneById(int id);

	List<PrenotazioneDto> findAllPrenotaziones();


    List<PrenotazioneDto> research(ResearchForm researchForm) throws ParseException;
}