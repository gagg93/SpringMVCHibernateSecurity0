package com.websystique.springmvc.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.websystique.springmvc.dto.ResearchForm;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.User;



@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(int id) {
		User user = getByKey(id);
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return user;
	}

	public User findByUsername(String username) {
		logger.info("Username : {}", username);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", username));

		User user = (User)crit.uniqueResult();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return users;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

	public List<User> research(ResearchForm researchForm) throws ParseException {
		Criteria crit = createEntityCriteria();
		if(researchForm.getField().equals("dataDiNascita")){
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(researchForm.getKey());
			crit.add(Restrictions.eq(researchForm.getField(), date1));

		}else{
			crit.add(Restrictions.like(researchForm.getField(), "%"+ researchForm.getKey()+"%"));
		}
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<User> users = crit.list();
		return users;
	}

}
