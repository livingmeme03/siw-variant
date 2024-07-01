package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.MangaValidator;
import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.MangaService;
import it.uniroma3.siw.service.VariantService;
import jakarta.validation.Valid;

@Controller
public class MangaController {

	/*#######################################################################################*/
	/*-------------------------------------SERVICES------------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private MangaService mangaService;
	
	@Autowired
	private VariantService variantService;
	
	/*#######################################################################################*/
	/*---------------------------------------VALIDATOR---------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private MangaValidator mangaValidator;

	//=======================================================================================\\
	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	//========================================================================================\\

	/*#######################################################################################*/
	/*-------------------------------------SHOW METHODS--------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/elencoManga")
	public String showElencoManga(Model model) {
		Iterable<Manga> allMangas = this.mangaService.findAllByOrderByTitoloAsc();
		model.addAttribute("allMangas", allMangas);
		return "elencoManga.html";
	}

	@GetMapping("/manga/{id}")
	public String showManga(@PathVariable("id") Long id, Model model) {
		model.addAttribute("manga", this.mangaService.findById(id));
		return "manga.html";
	}

	@GetMapping("/impostaMangaAVariant/{idVariant}")
	public String showAggiungiMangaAVariant(@PathVariable("idVariant") Long idVariant, Model model) {		
		Iterable<Manga> allMangas = this.mangaService.findAllByOrderByTitoloAsc();
		model.addAttribute("allMangas", allMangas);
		model.addAttribute("idVariant", idVariant);
		return "elencoMangaPerInserireInVariant.html";
	}
	
	/*#######################################################################################*/
	/*------------------------------------INSERT METHODS-------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/aggiungiManga")
	public String showFormAggiungiManga(Model model) {
		model.addAttribute("nuovoManga", new Manga());
		return "formAggiungiManga.html";
	}

	@PostMapping("/aggiungiManga")
	public String newManga(@Valid @ModelAttribute("nuovoManga") Manga manga, BindingResult bindingResult, Model model) {
		this.mangaValidator.validate(manga, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiManga.html";
		}
		else {
			this.mangaService.save(manga);
			return "redirect:manga/"+manga.getId();
		}
	}
	
	/*#######################################################################################*/
	/*-----------------------------------UPDATE METHODS--------------------------------------*/
	/*#######################################################################################*/
	
	@GetMapping("/elencoAggiornaManga")		//non servono validazioni 
	public String showElencoAggiornaManga(Model model) {
		model.addAttribute("elencoManga", this.mangaService.findAllByOrderByTitoloAsc());
		return "elencoAggiornaManga.html";
	}
	
	@GetMapping("/modificaVariantManga/{idManga}") 
	public String showModificaIngredientiRicetta(@PathVariable Long idManga, Model model) {
		List<Variant> variantDelManga = this.mangaService.findById(idManga).getVariants();
		List<Variant> variantDaAggiungere = (List<Variant>) this.variantService.findAllByOrderByNomeVariantAsc();
		variantDaAggiungere.removeAll(variantDelManga);
		model.addAttribute("variantDelManga", variantDelManga);
		model.addAttribute("variantDaAggiungere", variantDaAggiungere);
		model.addAttribute("manga", this.mangaService.findById(idManga));
		return "elencoVariantPerModificareManga.html";
	}
	
	@GetMapping("/aggiungiVariantAManga/{idManga}/{idVariant}")
	public String aggiungiVariantAManga(@PathVariable Long idManga, @PathVariable Long idVariant, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		variant.setManga(manga);
		manga.getVariants().add(variant);
		this.variantService.save(variant);
		this.mangaService.save(manga);
		return "redirect:/modificaVariantManga/" + idManga;
	}
	
	@GetMapping("/rimuoviVariantDaManga/{idManga}/{idVariant}") 
	public String rimuoviVariantDaManga(@PathVariable Long idManga, @PathVariable Long idVariant, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		variant.setManga(null);
		manga.getVariants().remove(variant);
		this.variantService.save(variant);
		this.mangaService.save(manga);
		return "redirect:/modificaVariantManga/" + idManga;
	}
	
	/*#######################################################################################*/
	/*------------------------------------REMOVE METHODS-------------------------------------*/
	/*#######################################################################################*/

	@GetMapping("/rimuoviManga")
	public String showFormRimuoviManga(Model model) {
		model.addAttribute("mangaDaRimuovere", new Manga());
		return "formRimuoviManga.html";
	}

	@PostMapping("/rimuoviManga")
	public String rimuoviManga(@Valid @ModelAttribute("mangaDaRimuovere") Manga manga, BindingResult bindingResult, Model model) {
		this.mangaValidator.validate(manga, bindingResult);
		
		if(bindingResult.hasErrors()) { //Significa che la variant esiste oppure ci sono altri errori
			if(bindingResult.getAllErrors().toString().contains("manga.duplicato")) { 
				this.mangaService.delete(manga);
				return "redirect:elencoManga"; //Unico caso funzionante!
			}
			return "formRimuoviManga.html"; //Ho problemi ma non il manga.duplicato, quindi lo user ha toppato
		}
		bindingResult.reject("manga.nonEsiste");
		return "formRimuoviManga.html"; //Ha inserito un manga che non esiste
		
	}


}
