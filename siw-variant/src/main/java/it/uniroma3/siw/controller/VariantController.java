package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.controller.validation.VariantValidator;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.VariantService;

@Controller
public class VariantController {
	
	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/
	
	@Autowired
	private VariantService variantService;
	
	/*##############################################################*/
	/*#########################VALIDATOR############################*/
	/*##############################################################*/
	
	@Autowired
	private VariantValidator variantValidator;

	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
//======================================================================\\
	
	/*##############################################################*/
	/*###########################/variant###########################*/
	/*##############################################################*/
	
	@GetMapping("/elencoVariant")
	public String showElencoVariant(Model model) {
		Iterable<Variant> allVariants = this.variantService.findAll();
		model.addAttribute("allVariants", allVariants);
		return "elencoVariant.html";
	}

	@GetMapping("/variant/{id}")
	public String showVariant(@PathVariable("id") Long id, Model model) {
		model.addAttribute("variant", this.variantService.findById(id));
		//model.addAttribute("manga", this.variantService.findById(id).getManga());
		//model.addAttribute("editore", this.variantService.findById(id).getEditore());
		return "variant.html";
	}
	
	
}
