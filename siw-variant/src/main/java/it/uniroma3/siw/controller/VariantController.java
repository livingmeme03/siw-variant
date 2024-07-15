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

	/*#######################################################################################*/
	/*-------------------------------------SERVICES------------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private VariantService variantService;

	@Autowired 
	private MangaService mangaService;

	@Autowired
	private EditoreService editoreService;

	@Autowired
	private AuthenticationController authenticationController;

	/*#######################################################################################*/
	/*---------------------------------------VALIDATOR---------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private VariantValidator variantValidator;


	//=======================================================================================\\
	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	//========================================================================================\\

	/*#######################################################################################*/
	/*-------------------------------------SHOW METHODS--------------------------------------*/
	/*#######################################################################################*/

	//Per tutti
	@GetMapping("/elencoVariant")
	public String showElencoVariant(Model model) {
		Iterable<Variant> allVariants = this.variantService.findAllByOrderByNomeVariantAsc();
		model.addAttribute("allVariants", allVariants);
		return "elencoVariant.html";
	}

	//Per tutti
	@GetMapping("/variant/{id}")
	public String showVariant(@PathVariable("id") Long id, Model model) {
		model.addAttribute("variant", this.variantService.findById(id));
		return "variant.html";
	}

	/*#######################################################################################*/
	/*------------------------------------INSERT METHODS-------------------------------------*/
	/*#######################################################################################*/

	//Per admin, identico, la il form ha il POST diverso
	@GetMapping("/admin/aggiungiVariant")
	public String showFormAggiungiVariantAdmin(Model model) {
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("nuovaVariant", variant);
		return "/admin/formAggiungiVariant.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiVariant")
	public String newVariantAdmin(@Valid @ModelAttribute("nuovaVariant") Variant variant, BindingResult bindingResult, Model model) {

		this.variantValidator.validate(variant, bindingResult);

		if(bindingResult.hasErrors()) {
			return "/admin/formAggiungiVariant.html";
		} else {
			this.variantService.save(variant);
			return "redirect:/variant/"+variant.getId();
		}
	}

	//Per editore
	@GetMapping("/aggiungiVariant")
	public String showFormAggiungiVariantEditore(Model model) {
		//NESSUN CONTROLLO EDITORE QUI
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("nuovaVariant", variant);
		return "formAggiungiVariant.html";
	}

	//Per editore
	@PostMapping("/aggiungiVariant")
	public String newVariantEditore(@Valid @ModelAttribute("nuovaVariant") Variant variant, BindingResult bindingResult, Model model) {
		//METTI CHE SI AUTOFILLA IL CAMPO EDITORE!!
		this.variantValidator.validate(variant, bindingResult);

		if(bindingResult.hasErrors()) {
			return "formAggiungiVariant.html";
		} else {
			Editore curr = this.authenticationController.getEditoreSessioneCorrente();
			variant.setEditore(curr);
			this.variantService.save(variant);
			return "redirect:/variant/"+variant.getId();
		}
	}

	/*#######################################################################################*/
	/*------------------------------------REMOVE METHODS-------------------------------------*/
	/*#######################################################################################*/

	//Per admin
	@GetMapping("/admin/rimuoviVariant")
	public String showFormRimuoviVariantAdmin(Model model) {
		model.addAttribute("elencoVariant", this.variantService.findAllByOrderByNomeVariantAsc());
		return "/admin/elencoRimuoviVariant.html";
	}

	//Per admin
	@GetMapping("/admin/rimuoviVariant/{idVariant}")
	public String rimuoviVariantAdmin(Model model, @PathVariable Long idVariant) {

		Variant variantDaRimuovere = this.variantService.findById(idVariant);
		if(variantDaRimuovere != null) {
			this.variantService.delete(variantDaRimuovere);
		}	
		return "redirect:/elencoVariant";
	}


	//Per editore
	@GetMapping("/rimuoviVariant")
	public String showFormRimuoviVariantEditore(Model model) {
		Editore curr = this.authenticationController.getEditoreSessioneCorrente();
		model.addAttribute("elencoVariantEditore", this.variantService.findAllByEditoreOrderByNomeVariantAsc(curr));
		return "/elencoRimuoviVariantEditore.html";
	}
	
	//Per editore
	@GetMapping("rimuoviVariant/{idVariant}")
	public String rimuoviVariantEditore(Model model, @PathVariable Long idVariant) {

		Variant variantDaRimuovere = this.variantService.findById(idVariant);
		if(variantDaRimuovere != null) {
			Editore editoreVariantDaModificare = variantDaRimuovere.getEditore();
			Editore curr = this.authenticationController.getEditoreSessioneCorrente();
			if(curr.equals(editoreVariantDaModificare)) {
				this.variantService.delete(variantDaRimuovere);
			}
		}
		return "redirect:/elencoVariant";
	}

	/*#######################################################################################*/
	/*-----------------------------------UPDATE METHODS--------------------------------------*/
	/*#######################################################################################*/


	/*--------------------------------------SET EDITORE--------------------------------------*/

	//Per admin
	@GetMapping("/admin/elencoAggiornaVariant")
	public String showElencoAggiornaVariantAdmin(Model model) {
		model.addAttribute("allVariants", this.variantService.findAllByOrderByNomeVariantAsc());
		return "/admin/elencoAggiornaVariant.html";
	}

	//PASSAGGIO INTERMEDIO DENTRO EditoreController -> @GetMapping("/impostaEditoreAVariant/{idVariant}")

	//Per admin
	@GetMapping("/admin/impostaEditoreAVariant/{idVariant}/{idEditore}")
	public String impostaEditoreAVariantAdmin(@PathVariable("idVariant") Long idVariant, @PathVariable("idEditore") Long idEditore, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Editore editore = this.editoreService.findById(idEditore);
		if(variant!=null && editore!=null) {
			variant.setEditore(editore);
			this.variantService.save(variant);
			return "redirect:/variant/" + variant.getId();
		}
		return "redirect:/admin/elencoAggiornaVariant";
	}

	//Per editore
	@GetMapping("/elencoAggiornaVariant")
	public String showElencoAggiornaVariantEditore(Model model) {
		Editore curr = this.authenticationController.getEditoreSessioneCorrente();
		model.addAttribute("allVariants", this.variantService.findAllByEditoreOrderByNomeVariantAsc(curr));
		return "elencoAggiornaVariant.html";
	}

	/*-------------------------------------SET MANGA----------------------------------------*/

	//PASSAGGIO INTERMEDIO DENTRO MangaController -> @GetMapping("/impostaMangaAVariant/{idVariant}")

	//Per admin
	@GetMapping("/admin/impostaMangaAVariant/{idVariant}/{idManga}")
	public String impostaMangaAVariantAdmin(@PathVariable("idVariant") Long idVariant, @PathVariable("idManga") Long idManga, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		if(variant!=null && manga!=null) {
			variant.setManga(manga);
			this.variantService.save(variant);
			return "redirect:/variant/" + variant.getId();
		}
		return "redirect:/admin/elencoAggiornaVariant";
	}


	//Per editore
	@GetMapping("/impostaMangaAVariant/{idVariant}/{idManga}")
	public String impostaMangaAVariantEditore(@PathVariable("idVariant") Long idVariant, @PathVariable("idManga") Long idManga, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		if(variant!=null) {
			if(manga!=null) {
				Editore editoreVariantDaModificare = variant.getEditore();
				Editore curr = this.authenticationController.getEditoreSessioneCorrente();
				if(curr.equals(editoreVariantDaModificare)) {
					variant.setManga(manga);
					this.variantService.save(variant);
				}
			}
			return "redirect:/variant/" + variant.getId();
		}
		return "redirect:/admin/elencoAggiornaVariant";
	}

	/*#######################################################################################*/
	/*-----------------------------------SEARCH METHODS--------------------------------------*/
	/*#######################################################################################*/

	/*-----------------------------------SEARCH BY NAME--------------------------------------*/
	//Tutto per tutti


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

	/*#######################################################################################*/
	/*----------------------------------SUPPORT METHODS--------------------------------------*/
	/*#######################################################################################*/


}
