package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.EditoreValidator;
import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.service.EditoreService;
import jakarta.validation.Valid;

@Controller
public class EditoreController {

	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/

	@Autowired
	private EditoreService editoreService;

	@Autowired
	private EditoreValidator editoreValidator;

	//======================================================================\\
	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
	//======================================================================\\

	/*##############################################################*/
	/*########################/SHOW METHODS#########################*/
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

	/*##############################################################*/
	/*######################/INSERT METHODS#########################*/
	/*##############################################################*/

	@GetMapping("/aggiungiEditore")
	public String showFormAggiungiEditore(Model model) {
		model.addAttribute("nuovoEditore", new Editore());
		return "formAggiungiEditore.html";
	}

	@PostMapping("/aggiungiEditore")
	public String newEditore(@Valid @ModelAttribute("nuovoEditore") Editore editore, BindingResult bindingResult, Model model) {
		this.editoreValidator.validate(editore, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiEditore.html";
		}
		else {
			this.editoreService.save(editore);
			return "redirect:editore/"+editore.getId();
		}
	}
}
