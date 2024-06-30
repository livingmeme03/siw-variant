package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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


	//======================================================================\\
	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
	//======================================================================\\

	/*##############################################################*/
	/*########################/SHOW METHODS#########################*/
	/*##############################################################*/

	@GetMapping("/elencoVariant")
	public String showElencoVariant(Model model) {
		Iterable<Variant> allVariants = this.variantService.findAllByOrderByNomeVariantAsc();
		model.addAttribute("allVariants", allVariants);
		return "elencoVariant.html";
	}

	@GetMapping("/variant/{id}")
	public String showVariant(@PathVariable("id") Long id, Model model) {
		model.addAttribute("variant", this.variantService.findById(id));
		return "variant.html";
	}

	/*##############################################################*/
	/*######################/INSERT METHODS#########################*/
	/*##############################################################*/

	@GetMapping("/aggiungiVariant")
	public String showFormAggiungiVariant(Model model) {
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("nuovaVariant", variant);
		return "formAggiungiVariant.html";
	}

	@PostMapping("/aggiungiVariant")
	public String newVariant(@Valid @ModelAttribute("nuovaVariant") Variant variant, BindingResult bindingResult, Model model) {

		this.variantValidator.validate(variant, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "formAggiungiVariant.html";
		} else {
			this.variantService.save(variant);
			return "redirect:variant/"+variant.getId();
		}
	}

	/*##############################################################*/
	/*######################/REMOVE METHODS#########################*/
	/*##############################################################*/

	@GetMapping("/rimuoviVariant")
	public String showFormRimuoviVariant(Model model) {
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("variantDaRimuovere", variant);

		return "formRimuoviVariant.html";
	}

	@PostMapping("/rimuoviVariant")
	public String rimuoviVariant(@Valid @ModelAttribute("variantDaRimuovere") Variant variant, BindingResult bindingResult, Model model) {
		
		this.variantValidator.validate(variant, bindingResult);
		
		if(bindingResult.hasErrors()) { //Significa che la variant esiste oppure ci sono altri errori
			if(bindingResult.getAllErrors().toString().contains("variant.duplicata")) {			
				this.variantService.delete(this.variantService.findByNomeVariant(variant.getNomeVariant())); //delete entity of db
				return "redirect:elencoVariant"; //Unico caso funzionante!
			}
			System.out.println(bindingResult.getAllErrors().toString());
			return "formRimuoviVariant.html"; //Ho problemi ma non il variant.duplicata, quindi lo user ha toppato
		}
		
		bindingResult.reject("variant.nonEsiste");
		return "formRimuoviVariant.html"; //Ha inserito una variant che non esiste
	}
	
	/*##############################################################*/
	/*####################ADD EDITORE TO VARIANT####################*/
	/*##############################################################*/
	
	@GetMapping("/elencoAggiornaVariant")
	public String showElencoAggiornaVariant(Model model) {
		model.addAttribute("allVariants", this.variantService.findAllByOrderByNomeVariantAsc());
		return "elencoAggiornaVariant.html";
	}
	
	//PASSAGGIO INTERMEDIO DENTRO EditoreController -> @GetMapping("/impostaEditoreAVariant/{idVariant}")
	
	@GetMapping("/impostaEditoreAVariant/{idVariant}/{idEditore}")
	public String impostaEditoreAVariant(@PathVariable("idVariant") Long idVariant, @PathVariable("idEditore") Long idEditore, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Editore editore = this.editoreService.findById(idEditore);
		if(variant!=null && editore!=null) {
			variant.setEditore(editore);
			this.variantService.save(variant);
		}
		return "redirect:../../variant/" + variant.getId();
	}
	
	/*##############################################################*/
	/*#####################ADD MANGA TO VARIANT#####################*/
	/*##############################################################*/
	
	//PASSAGGIO INTERMEDIO DENTRO MangaController -> @GetMapping("/impostaMangaAVariant/{idVariant}")
	
	@GetMapping("/impostaMangaAVariant/{idVariant}/{idManga}")
	public String impostaMangaAVariant(@PathVariable("idVariant") Long idVariant, @PathVariable("idManga") Long idManga, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		if(variant!=null && manga!=null) {
			variant.setManga(manga);
			this.variantService.save(variant);
		}
		return "redirect:../../variant/" + variant.getId();
	}
	
	/*##############################################################*/
	/*####################SEARCH VARIANT BY NAME####################*/
	/*##############################################################*/
	
	@GetMapping("/formRicercaVariant")
	public String showFormRicercaVariant(Model model) {
		model.addAttribute("variantInfos", new Variant());
		return "formRicercaVariant.html";
	}
	
	@PostMapping("/ricercaPerNomeVariant")
	public String showVariantConStessoNome(@Valid @ModelAttribute("variantInfos") Variant variant,
							BindingResult bindingResult, Model model) {	
		
		if(bindingResult.hasFieldErrors("nomeVariant")) {
			return "redirect:/variant/-1"; //Un modo carino per sfruttare il template della variant per una variant che non esiste
		}
		
		variant = this.variantService.findByNomeVariant(variant.getNomeVariant());
		//Questa è sicuramente una sola
		if(variant!=null) {
			return "redirect:variant/"+variant.getId();
		}
		
		return "redirect:/variant/-1"; //Un modo carino per sfruttare il template della variant per una variant che non esiste
	}
	
	/*##############################################################*/
	/*######################/SUPPORT METHODS########################*/
	/*##############################################################*/


}
