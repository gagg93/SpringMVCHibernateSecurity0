package com.websystique.springmvc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.websystique.springmvc.dto.ResearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.UserService;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@RequestMapping(value = { "/", "/userlist" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) throws ParseException {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "userslist";
	}

	@RequestMapping(value = { "/", "/userlist" }, method = RequestMethod.POST)
	public String researchUsers(ResearchForm researchForm,ModelMap model,BindingResult result) throws ParseException {
		try{
			List<User> users=userService.research(researchForm);
			model.addAttribute("users", users);
		}catch (Exception e){
			FieldError formatodata =new FieldError("researchform","key",messageSource.getMessage("date.notvalid", null, Locale.getDefault()));
			result.addError(formatodata);
			List<User> users = userService.findAllUsers();
			model.addAttribute("users", users);
			model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("researchform", new ResearchForm());
			return "userslist";

		}
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "userslist";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {


		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			return "registration";
		}
		User user1=userService.findByUsername(user.getUsername());
		if(user1!=null){
			FieldError usernameError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
		    result.addError(usernameError);
			model.addAttribute("loggedinuser", getPrincipal());
			return "registration";
		}
		userService.saveUser(user);
		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "user");
		//return "success";
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-user-{id}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable int id, ModelMap model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/edit-profile" }, method = RequestMethod.GET)
	public String editProfile(ModelMap model) {
		User user = userService.findByUsername(getPrincipal());
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/edit-profile" }, method = RequestMethod.POST)
	public String updateProfile(@Valid User user, BindingResult result,ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("edit", true);
			return "registration";
		}
		userService.updateUser(user);
		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "user");
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-user-{Id}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable int Id) {

		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("edit", true);
			return "registration";
		}
		userService.updateUser(user);
		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "user");
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/delete-user-{id}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable int id) {
		userService.deleteUserById(id);
		return "redirect:/userlist";
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
	    } else {
			return "redirect:/userlist";
	    }
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	private String getPrincipal(){
		String userName;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
}