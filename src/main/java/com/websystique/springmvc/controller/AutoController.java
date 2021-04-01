package com.websystique.springmvc.controller;

import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.model.Auto;
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

	@RequestMapping(value = {"/newauto"}, method = RequestMethod.GET)
	public String newAuto(ModelMap model) {
		Auto auto = new Auto();
		model.addAttribute("auto", auto);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "autoregistration";
	}

	@RequestMapping(value = {"/newauto"}, method = RequestMethod.POST)
	public String saveAuto(@Valid Auto auto, BindingResult result,
						   ModelMap model) {


		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			return "autoregistration";
		}
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
	
		return "registrationsuccess";
	}

	@RequestMapping(value = {"/edit-auto-{id}"}, method = RequestMethod.GET)
	public String editAuto(@PathVariable int id, ModelMap model) {
		Auto auto = autoService.findById(id);
		model.addAttribute("auto", auto);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "autoregistration";
	}

	@RequestMapping(value = {"/edit-auto-{id}"}, method = RequestMethod.POST)
	public String updateAuto(@PathVariable int id, @Valid Auto auto, BindingResult result,
							 ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("edit", true);
			return "autoregistration";
		}
		autoService.updateAuto(auto);

		model.addAttribute("success", "auto " + auto.getTarga() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "auto");
		return "registrationsuccess";
	}

	@RequestMapping(value = {"/delete-auto-{id}"}, method = RequestMethod.GET)
	public String deleteUser(@PathVariable int id) {
		autoService.deleteAutoById(id);
		return "redirect:/autolist";
	}

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

	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}