package com.websystique.springmvc.dao;

import java.text.ParseException;
import java.util.List;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.User;


public interface UserDao {

	User findById(int id);
	
	User findByUsername(String username);
	
	void save(User user);
	
	void deleteById(int id);
	
	List<User> findAllUsers();

	public List<User> research(ResearchForm researchForm) throws ParseException;

}

