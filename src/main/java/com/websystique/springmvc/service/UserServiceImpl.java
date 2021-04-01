package com.websystique.springmvc.service;

import java.text.ParseException;
import java.util.List;

import com.websystique.springmvc.dto.ResearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByUsername(String username) {
		User user = dao.findByUsername(username);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setUsername(user.getUsername());
			entity.setDataDiNascita(user.getDataDiNascita());

		}
	}

	
	public void deleteUserById(int id) {
		dao.deleteById(id);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public List<User> research(ResearchForm researchForm) throws ParseException {
		switch(researchForm.getField()){
			case "Username":researchForm.setField("username");break;
			case "First name":researchForm.setField("firstName");break;
			case "Last name":researchForm.setField("lastName");break;
			case "Data di nascita":	researchForm.setField("dataDiNascita");
									break;
		}

		return  dao.research(researchForm);
	}
	
}
