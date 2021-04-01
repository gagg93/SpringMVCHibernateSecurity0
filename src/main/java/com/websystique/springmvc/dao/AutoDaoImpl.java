package com.websystique.springmvc.dao;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository("autoDao")
public class AutoDaoImpl extends AbstractDao<Integer, Auto> implements AutoDao {

	static final Logger logger = LoggerFactory.getLogger(AutoDaoImpl.class);
	
	public Auto findById(int id) {
		Auto auto = getByKey(id);
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return auto;
	}

	public Auto findByTarga(String targa) {
		logger.info("targa : {}", targa);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("targa", targa));
		Auto auto = (Auto)crit.uniqueResult();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return auto;
	}

	@SuppressWarnings("unchecked")
	public List<Auto> findAllAutos() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("targa"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Auto> autos = (List<Auto>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return autos;
	}

	public void save(Auto auto) {
		persist(auto);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Auto auto = (Auto) crit.uniqueResult();
		delete(auto);
	}

	public List<Auto> research(ResearchForm researchForm) {
		Criteria crit = createEntityCriteria();
		if(researchForm.getField().equals("annoImmatricolazione")){
			crit.add(Restrictions.like(researchForm.getField(), Integer.valueOf(researchForm.getKey())));
		}else{
			crit.add(Restrictions.like(researchForm.getField(), "%"+ researchForm.getKey()+"%"));
		}
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Auto> autos = crit.list();
		return autos;
	}

}
