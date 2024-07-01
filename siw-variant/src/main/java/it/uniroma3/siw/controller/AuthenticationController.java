package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	/*#######################################################################################*/
	/*---------------------------------------SERVICES----------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	/*#######################################################################################*/
	/*---------------------------------------REGISTER----------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("editore", new Editore());
		return "formRegister.html";
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public String newUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, 
						  @Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResultCredentials,
						  @Valid @ModelAttribute("editore") Editore editore, BindingResult bindingResultEditore, 
						  Model model) {
		user.setEditore(editore);
		credentials.setUser(user);
		this.credentialsValidator.validate(credentials, bindingResultCredentials);

		if(bindingResultUser.hasErrors() || bindingResultCredentials.hasErrors() || bindingResultEditore.hasErrors()) {
			model.addAttribute("userErrors", bindingResultUser);
			model.addAttribute("editoreErrors", bindingResultEditore);
			return "formRegister.html";
		} else {
			credentialsService.saveCredentials(credentials); //Role lo setto qui, anche l'hash della pwd
			return "redirect:login"; //finito di registrare redirecto a /
		}

	}

	/*#######################################################################################*/
	/*-----------------------------------------INDEX-----------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/")
	public String showIndex(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		}
		else {
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/adminIndex.html";
			}
			return "index.html";
		}

	}
	

	/*#######################################################################################*/
	/*-----------------------------------------LOGIN-----------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}
	
	/*#######################################################################################*/
	/*------------------------------------SUPPORT METHODS------------------------------------*/
	/*#######################################################################################*/
	
	
	public static Editore getEditoreSessioneCorrente() {
		CredentialsService temp = new CredentialsService();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials utenteSessioneCorrente = temp.findByUsername(user.getUsername());
		Editore editore = utenteSessioneCorrente.getUser().getEditore();
		return editore;
	}

	
	


}
