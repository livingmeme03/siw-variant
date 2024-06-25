package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		Credentials newCredentials = new Credentials();
		newCredentials.setUser(new User());
		model.addAttribute("credentials", newCredentials);
		return "formRegister.html";
	}

	@PostMapping("/register")
	public String newUser(@ModelAttribute("credentials") Credentials credentials, Model model) {
		credentials.setRole(Credentials.EDITORE_ROLE);
		this.credentialsService.saveCredentials(credentials);
		return "redirect:/";				//finito di registrare redirecto a /
	}

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
				return "admin/index.html";
			}
			return "editore/index.html";
		}

	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}
	
	@GetMapping("/success")
	public String showSuccessPage(Model model) {
		return "success.html";
	}


}
