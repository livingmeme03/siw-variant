package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.VariantValidator;
import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.EditoreService;
import it.uniroma3.siw.service.MangaService;
import it.uniroma3.siw.service.VariantService;
import jakarta.validation.Valid;

@Controller
public class VariantController {
	
	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/
	
	@Autowired
	private VariantService variantService;
	
	@Autowired 
	private MangaService mangaService;
	
	@Autowired
	private EditoreService editoreService;
	
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
	
	@GetMapping("/aggiungiVariant")
	public String showFormAggiungiVariant(Model model) {
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("nuovaVariant", variant);
		model.addAttribute("manga", new Manga());
		model.addAttribute("editore", new Editore());
		return "formAggiungiVariant.html";
	}
	
	@PostMapping("/aggiungiVariant")
	public String newVariant(@Valid @ModelAttribute("nuovaVariant") Variant variant, BindingResult bindingResult, 
			@ModelAttribute("manga") Manga manga, 
			@ModelAttribute("editore") Editore editore, Model model) {
		this.mangaService.save(manga);
		this.editoreService.save(editore);
		variant.setManga(manga);
		variant.setEditore(editore);
		this.variantValidator.validate(variant, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiVariant.html";
		}
		else {
		System.out.println(variant.getManga().toString());
		System.out.println(variant.getEditore().toString());
		variant.setManga(this.mangaService.findByTitoloAndAutore(variant.getManga().getTitolo(), variant.getManga().getAutore()));
		variant.setEditore(this.editoreService.findByNomeAndNazione(variant.getEditore().getNome(), variant.getEditore().getNazione()));
		System.out.println(variant.getManga().toString());
		System.out.println(variant.getEditore().toString());
		this.variantService.save(variant);
		return "redirect:variant/"+variant.getId();
		}
	}
	
	
}
