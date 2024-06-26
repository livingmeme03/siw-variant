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

	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;
	
	/*##############################################################*/
	/*##########################REGISTER############################*/
	/*##############################################################*/

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister.html";
	}

	@PostMapping("/register")
	public String newUser(@ModelAttribute("user") User user, @ModelAttribute("credentials") Credentials credentials, Model model) {
		credentials.setUser(user);
        credentialsService.saveCredentials(credentials); //Role lo setto qui, anche l'hash della pwd
		return "redirect:login"; //finito di registrare redirecto a /
	}
	
	/*##############################################################*/
	/*############################INDEX#############################*/
	/*##############################################################*/

	@GetMapping("/")
	public String showIndex(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "placeholder.html";
		}
		else {
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/index.html";
			}
			return "index.html";
		}
		
	}
	
	/*##############################################################*/
	/*###########################LOGIN##############################*/
	/*##############################################################*/
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}
	

}
