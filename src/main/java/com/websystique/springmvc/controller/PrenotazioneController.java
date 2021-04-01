package com.websystique.springmvc.controller;

import com.websystique.springmvc.dto.ControlDatesForm;
import com.websystique.springmvc.dto.PrenotazioneDto;
import com.websystique.springmvc.dto.ResearchForm;
import com.websystique.springmvc.service.AutoService;
import com.websystique.springmvc.service.PrenotazioneService;
import com.websystique.springmvc.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class PrenotazioneController {

	@Autowired
	PrenotazioneService prenotazioneService;

	@Autowired
	UserService userService;

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
	@RequestMapping(value = {"/prenotazionelist"}, method = RequestMethod.GET)
	public String listPrenotazioni(ModelMap model) {
		if(!userService.findByUsername(getPrincipal()).getAdmin()){
			return "redirect:prenotazioni-user-0";
		}
		List<PrenotazioneDto> prenotaziones = prenotazioneService.findAllPrenotaziones();
		model.addAttribute("prenotaziones", prenotaziones);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "prenotazionelist";
	}

	@RequestMapping(value = {"/prenotazionelist"}, method = RequestMethod.POST)
	public String researchPrenotazioni(ResearchForm researchForm, ModelMap model) throws ParseException {
		List<PrenotazioneDto> prenotaziones = prenotazioneService.research(researchForm);
			model.addAttribute("prenotaziones", prenotaziones);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "prenotazionelist";
	}

	@RequestMapping(value = {"/prenotazioni-user-{id}"}, method = RequestMethod.GET)
	public String listPrenotazionibyUser(@PathVariable int id,ModelMap model) {
		if(id==0){
			id=userService.findByUsername(getPrincipal()).getId();
		}
		int thisid=userService.findByUsername(getPrincipal()).getId();
		List<PrenotazioneDto> prenotaziones = prenotazioneService.findByUser(id);
		model.addAttribute("prenotaziones", prenotaziones);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "prenotazionelist";
	}

	@RequestMapping(value = {"/prenotazioni-user-{id}"}, method = RequestMethod.POST)
	public String researchPrenotazioniByUser(ResearchForm researchForm, ModelMap model) throws ParseException {
		List<PrenotazioneDto> prenotazioni = prenotazioneService.research(researchForm);
		List<PrenotazioneDto> prenotaziones=new ArrayList<>();
		if(!userService.findByUsername(getPrincipal()).getAdmin()) {
			for (PrenotazioneDto var:
					prenotazioni) {
				if(var.getUsername().equals(getPrincipal())){
					prenotaziones.add(var);
				}
			}
		}
		model.addAttribute("prenotaziones", prenotaziones);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("researchform", new ResearchForm());
		return "prenotazionelist";
	}

	@RequestMapping(value = {"/newprenotazione"}, method = RequestMethod.GET)
	public String newPrenotazione(ModelMap model) {
		PrenotazioneDto prenotazione = new PrenotazioneDto();
		model.addAttribute("prenotazioneDto", prenotazione);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "prenotazioneregistration";
	}

	@RequestMapping(value = {"/newprenotazione"}, method = RequestMethod.POST)
	public String savePrenotazione(@ModelAttribute("prenotazioneDto")@Valid PrenotazioneDto prenotazioneDto, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("loggedinuser", getPrincipal());
			return "prenotazioneregistration";
		}
		ControlDatesForm controlDatesForm=controlDates(new ControlDatesForm(model,result,prenotazioneDto,new String()));
		if (controlDatesForm.getErrorString() != null){
			return controlDatesForm.getErrorString();
		}
		prenotazioneService.savePrenotazione(prenotazioneDto);

		model.addAttribute("success", "prenotazione " + prenotazioneDto.getId() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "prenotazione");

		return "registrationsuccess";
	}

	private ControlDatesForm controlDates(ControlDatesForm controlDatesForm) {

		if( userService.findByUsername(controlDatesForm.getPrenotazioneDto().getUsername())==null){
			FieldError autouser =new FieldError("prenotazioneDto","username",messageSource.getMessage("non.existing.username", new String[]{controlDatesForm.getPrenotazioneDto().getTarga()}, Locale.getDefault()));
			controlDatesForm.getBindingResult().addError(autouser);
			controlDatesForm.getModelMap().addAttribute("loggedinuser", getPrincipal());
			controlDatesForm.setErrorString("prenotazioneregistration");
			return controlDatesForm;
		}
		if(autoService.findByTarga(controlDatesForm.getPrenotazioneDto().getTarga())==null){
			FieldError autouser =new FieldError("prenotazioneDto","targa",messageSource.getMessage("non.existing.targa", new String[]{controlDatesForm.getPrenotazioneDto().getTarga()}, Locale.getDefault()));
			controlDatesForm.getBindingResult().addError(autouser);
			controlDatesForm.getModelMap().addAttribute("loggedinuser", getPrincipal());
			controlDatesForm.setErrorString("prenotazioneregistration");
			return controlDatesForm;
		}
		if (controlDatesForm.getPrenotazioneDto().getDataDiFine()==null||controlDatesForm.getPrenotazioneDto().getDataDiInizio()==null){
			FieldError inversiondate =new FieldError("prenotazioneDto","dataDiFine",messageSource.getMessage("date.notvalid", null, Locale.getDefault()));
			controlDatesForm.getBindingResult().addError(inversiondate);
			controlDatesForm.getModelMap().addAttribute("loggedinuser", getPrincipal());
			controlDatesForm.setErrorString("prenotazioneregistration");
			return controlDatesForm;
		}
		if(controlDatesForm.getPrenotazioneDto().getDataDiFine().before(controlDatesForm.getPrenotazioneDto().getDataDiInizio())){
			FieldError inversiondate =new FieldError("prenotazioneDto","dataDiFine",messageSource.getMessage("prenotazione.inversiondate", null, Locale.getDefault()));
			controlDatesForm.getBindingResult().addError(inversiondate);
			controlDatesForm.getModelMap().addAttribute("loggedinuser", getPrincipal());
			controlDatesForm.setErrorString("prenotazioneregistration");
			return controlDatesForm;
		}
		List<PrenotazioneDto> prenotazioneDtos=prenotazioneService.findByAuto(autoService.findByTarga(controlDatesForm.getPrenotazioneDto().getTarga()));
		for (PrenotazioneDto var :
				prenotazioneDtos) {
			if(		var.getDataDiFine().equals(controlDatesForm.getPrenotazioneDto().getDataDiFine()) || var.getDataDiInizio().equals(controlDatesForm.getPrenotazioneDto().getDataDiInizio()) ||
					var.getDataDiInizio().equals(controlDatesForm.getPrenotazioneDto().getDataDiFine()) || var.getDataDiFine().equals(controlDatesForm.getPrenotazioneDto().getDataDiInizio()) ||
					(controlDatesForm.getPrenotazioneDto().getDataDiFine().before(var.getDataDiFine()) && controlDatesForm.getPrenotazioneDto().getDataDiFine().after(var.getDataDiInizio())) ||
					(controlDatesForm.getPrenotazioneDto().getDataDiInizio().before(var.getDataDiFine()) && controlDatesForm.getPrenotazioneDto().getDataDiInizio().after(var.getDataDiInizio()))){
				if( controlDatesForm.getPrenotazioneDto().getId()!= var.getId()){
				FieldError troppotardi =new FieldError("prenotazioneDto","dataDiFine",messageSource.getMessage("prenotazione.dateoccupate", null, Locale.getDefault()));
				controlDatesForm.getBindingResult().addError(troppotardi);
				controlDatesForm.getModelMap().addAttribute("loggedinuser", getPrincipal());
				controlDatesForm.setErrorString("prenotazioneregistration");
				return controlDatesForm;}
			}
		}
		controlDatesForm.setErrorString(null);
		return controlDatesForm;
	}

	@RequestMapping(value = {"/edit-prenotazione-{id}"}, method = RequestMethod.GET)
	public String editPrenotazione(@PathVariable int id, ModelMap model) {
		PrenotazioneDto prenotazione = prenotazioneService.findById(id);
		Date twodays=new Date(System.currentTimeMillis()+172800000);
		if(prenotazione.getDataDiInizio().before(twodays)){
			return "editregistrationimpossible";
		}
		model.addAttribute("prenotazioneDto", prenotazione);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "prenotazioneregistration";
	}

	@RequestMapping(value = {"/edit-prenotazione-{id}"}, method = RequestMethod.POST)
	public String updatePrenotazione(@PathVariable int id,@Valid PrenotazioneDto prenotazioneDto, BindingResult result,
							 ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("edit", true);
			return "prenotazioneregistration";
		}

		ControlDatesForm controlDatesForm=controlDates(new ControlDatesForm(model,result,prenotazioneDto,new String()));
		if (controlDatesForm.getErrorString() != null){
			return controlDatesForm.getErrorString();
		}
		prenotazioneDto.setApprovata(false);
		prenotazioneService.updatePrenotazione(prenotazioneDto);

		model.addAttribute("success", "prenotazione " + prenotazioneDto.getId() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("returnpage", "prenotazione");
		return "registrationsuccess";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = {"/delete-prenotazione-{id}"}, method = RequestMethod.GET)
	public String deletePrenotazione(@PathVariable int id) {
		PrenotazioneDto prenotazione = prenotazioneService.findById(id);
		Date twodays=new Date(System.currentTimeMillis()+172800000);
		if(prenotazione.getDataDiInizio().before(twodays)){
			return "editregistrationimpossible";
		}
		prenotazioneService.deletePrenotazioneById(id);
		return "redirect:/prenotazioni-user-0";
	}

	@RequestMapping(value = {"/approve-prenotazione-{id}"}, method = RequestMethod.GET)
	public String approvePrenotazione(@PathVariable int id) {
		PrenotazioneDto prenotazione=prenotazioneService.findById(id);
		prenotazione.setApprovata(true);
		prenotazioneService.updatePrenotazione(prenotazione);
		return "redirect:/prenotazionelist";
	}

	@RequestMapping(value = {"/disapprove-prenotazione-{id}"}, method = RequestMethod.GET)
	public String disapprovePrenotazione(@PathVariable int id) {
		PrenotazioneDto prenotazione=prenotazioneService.findById(id);
		prenotazione.setApprovata(false);
		prenotazioneService.updatePrenotazione(prenotazione);
		return "redirect:/prenotazionelist";
	}

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