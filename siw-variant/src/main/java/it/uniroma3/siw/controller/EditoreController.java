package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.service.EditoreService;

@Controller
public class EditoreController {
	
	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/
	
	@Autowired
	private EditoreService editoreService;

	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
//======================================================================\\
	
	/*##############################################################*/
	/*###########################/Editori###########################*/
	/*##############################################################*/
	
	@GetMapping("/elencoEditori")
	public String showElencoEditori(Model model) {
		Iterable<Editore> allEditori = this.editoreService.findAll();
		model.addAttribute("allEditori", allEditori);
		return "elencoEditori.html";
	}
	
	@GetMapping("/editore/{id}")
	public String showEditore(Model model) {
		
		return "editore.html";
	}
	
	
}
