package it.uniroma3.siw.controller;

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
import it.uniroma3.siw.service.MangaService;
import jakarta.validation.Valid;

@Controller
public class MangaController {

	/*#######################################################################################*/
	/*-------------------------------------SERVICES------------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private MangaService mangaService;
	
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
