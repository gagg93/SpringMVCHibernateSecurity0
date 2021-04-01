package com.websystique.springmvc.controller;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AutoController {

	@Autowired
	AutoService autoService;


	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;


	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = {"/autolist"}, method = RequestMethod.GET)
	public String listAutos(ModelMap model) {

		List<Auto> autos = autoService.findAllAutos();
		model.addAttribute("autos", autos);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "autolist";
	}

	@RequestMapping(value = {"/autolist"}, method = RequestMethod.POST)
	public String researchAutos(ResearchForm researchForm,ModelMap model) {

		List<Auto> autos=autoService.research(researchForm);
		model.addAttribute("autos", autos);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "autolist";
	}




	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = {"/newauto"}, method = RequestMethod.GET)
	public String newAuto(ModelMap model) {
		Auto auto = new Auto();
		model.addAttribute("auto", auto);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "autoregistration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = {"/newauto"}, method = RequestMethod.POST)
	public String saveAuto(@Valid Auto auto, BindingResult result,
						   ModelMap model) {


		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			return "autoregistration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
		 * and applying it on field [sso] of Model class [User].
		 *
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 *
		 */
		if(autoService.findByTarga(auto.getTarga())!=null){
			FieldError ssoError =new FieldError("auto","targa",messageSource.getMessage("non.unique.targa", new String[]{auto.getTarga()}, Locale.getDefault()));
		    result.addError(ssoError);
			model.addAttribute("loggedinuser", getPrincipal());
			return "autoregistration";
		}

		autoService.saveAuto(auto);

		model.addAttribute("success", "auto " + auto.getTarga() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "auto");
		//return "success";
		return "registrationsuccess";
	}


	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = {"/edit-auto-{id}"}, method = RequestMethod.GET)
	public String editAuto(@PathVariable int id, ModelMap model) {
		Auto auto = autoService.findById(id);
		model.addAttribute("auto", auto);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "autoregistration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = {"/edit-auto-{id}"}, method = RequestMethod.POST)
	public String updateAuto(@PathVariable int id, @Valid Auto auto, BindingResult result,
							 ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("edit", true);
			return "autoregistration";
		}

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


		autoService.updateAuto(auto);

		model.addAttribute("success", "auto " + auto.getTarga() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "auto");
		return "registrationsuccess";
	}


	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = {"/delete-auto-{id}"}, method = RequestMethod.GET)
	public String deleteUser(@PathVariable int id) {
		autoService.deleteAutoById(id);
		return "redirect:/autolist";
	}


	/**
	 * This method will provide UserProfile list to views
	 */
	/*@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}*/

	/**
	 * This method handles Access-Denied redirect.
	 */
	/*@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}*/


	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}