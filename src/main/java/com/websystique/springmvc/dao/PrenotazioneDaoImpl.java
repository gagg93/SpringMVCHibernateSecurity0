package com.websystique.springmvc.dao;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.Prenotazione;
import com.websystique.springmvc.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository("prenotazioneDao")
public class PrenotazioneDaoImpl extends AbstractDao<Integer, Prenotazione> implements PrenotazioneDao {

	static final Logger logger = LoggerFactory.getLogger(PrenotazioneDaoImpl.class);
	
	public Prenotazione findById(int id) {
		Prenotazione prenotazione = getByKey(id);
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return prenotazione;
	}

	public List<Prenotazione> findByUser(User user) {
		logger.info("Username : {}", user);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("user", user));
		List<Prenotazione> prenotazione = crit.list();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return prenotazione;
	}

	@Override
	public List<Prenotazione> findByAuto(Auto auto) {
		logger.info("Username : {}", auto);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("auto", auto));
		List<Prenotazione> prenotazione = crit.list();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return prenotazione;
	}

	@SuppressWarnings("unchecked")
	public List<Prenotazione> findAllPrenotaziones() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("user"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Prenotazione> prenotaziones = (List<Prenotazione>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return prenotaziones;
	}

	public void save(Prenotazione prenotazione) {
		persist(prenotazione);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Prenotazione prenotazione = (Prenotazione)crit.uniqueResult();
		delete(prenotazione);
	}

	public List<Prenotazione> research(ResearchForm researchForm) throws ParseException {
		Criteria crit = createEntityCriteria();
		if(researchForm.getField().equals("dataDiInizio")||researchForm.getField().equals("dataDiFine")){
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(researchForm.getKey());
			crit.add(Restrictions.eq(researchForm.getField(), date1));
		}else {
			if (researchForm.getField().equals("targa")) {
				Criteria crittarga = crit.createCriteria("auto");
				crittarga.add(Restrictions.like(researchForm.getField(), "%" + researchForm.getKey() + "%"));
			} else {
				Criteria crittarga = crit.createCriteria("user");
				crittarga.add(Restrictions.like(researchForm.getField(), "%" + researchForm.getKey() + "%"));
			}
		}

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Prenotazione> prenotaziones = crit.list();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return prenotaziones;


	}
}




