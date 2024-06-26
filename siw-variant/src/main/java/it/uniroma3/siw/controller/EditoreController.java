package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	public String showEditore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("editore", this.editoreService.findById(id));
		//model.addAttribute("listaVariant", this.editoreService.findById(id).getVariantPubblicate());
		return "editore.html";
	}
	
	
}
