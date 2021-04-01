package com.websystique.springmvc.service;

import java.text.ParseException;
import java.util.List;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.User;


public interface UserService {
	
	User findById(int id);
	
	User findByUsername(String username);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(int id);

	List<User> findAllUsers();

	List<User> research(ResearchForm researchForm) throws ParseException;
	


}