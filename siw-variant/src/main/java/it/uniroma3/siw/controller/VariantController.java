package it.uniroma3.siw.controller;

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
		//model.addAttribute("manga", this.variantService.findById(id).getManga());
		//model.addAttribute("editore", this.variantService.findById(id).getEditore());
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

		this.variantValidator.validatePartial(variant, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "formAggiungiVariant.html";
		} else {
			this.variantService.save(variant);
			return "redirect:variant/"+variant.getId();
		}
	}
	
	
	@GetMapping("/aggiungiVariantCompleta")
	public String showFormAggiungiVariantCompleta(Model model) {
		Variant variant = new Variant();
		variant.setRarità(0);
		model.addAttribute("nuovaVariant", variant);
		model.addAttribute("manga", new Manga());
		model.addAttribute("editore", new Editore());

		this.addElencoNomiAndNazionalitàEditoriToModel(model);

		this.addElencoTitoloAndAutoremangaToModel(model);

		return "formAggiungiVariantCompleta.html";
	}

	@PostMapping("/aggiungiVariantCompleta")
	public String newVariantCompleta(@Valid @ModelAttribute("nuovaVariant") Variant variant, BindingResult bindingResult, 
			@ModelAttribute("manga") Manga manga, 
			@ModelAttribute("editore") Editore editore, Model model) {
		
		//Ricerca del manga relativo sulla base di titolo e autore, e assegnazione a Variant
		Manga mangaRelativo = this.mangaService.findByTitoloAndAutore(manga.getTitolo(),  manga.getAutore());
		variant.setManga(mangaRelativo);
		//Ricerca dell'editore relativo sulla base di nome e nazione, e assegnazione a Variant
		Editore editoreRelativo = this.editoreService.findByNomeAndNazione(editore.getNome(), editore.getNazione());
		variant.setEditore(editoreRelativo);

		this.variantValidator.validate(variant, bindingResult);
		if(bindingResult.hasErrors()) {
			if(mangaRelativo!=null) {
				model.addAttribute("manga", mangaRelativo); //Per la print del numero di volumi max
			}
			this.addElencoNomiAndNazionalitàEditoriToModel(model);

			this.addElencoTitoloAndAutoremangaToModel(model);
			return "formAggiungiVariantCompleta.html";
		}

		else {
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
		model.addAttribute("manga", new Manga());
		model.addAttribute("editore", new Editore());

		this.addElencoNomiAndNazionalitàEditoriToModel(model);

		this.addElencoTitoloAndAutoremangaToModel(model);

		return "formRimuoviVariant.html";
	}

	@PostMapping("/rimuoviVariant")
	public String rimuoviVariant(@Valid @ModelAttribute("variantDaRimuovere") Variant variant, BindingResult bindingResult, 
					@ModelAttribute("manga") Manga manga, @ModelAttribute("editore") Editore editore,
				Model model) {

		//Ricerca del manga relativo sulla base di titolo e autore, e assegnazione a Variant
		Manga mangaRelativo = this.mangaService.findByTitoloAndAutore(manga.getTitolo(),  manga.getAutore());
		variant.setManga(mangaRelativo);
		//Ricerca dell'editore relativo sulla base di nome e nazione, e assegnazione a Variant
		Editore editoreRelativo = this.editoreService.findByNomeAndNazione(editore.getNome(), editore.getNazione());
		variant.setEditore(editoreRelativo);
		
		this.addElencoNomiAndNazionalitàEditoriToModel(model);

		this.addElencoTitoloAndAutoremangaToModel(model);

		this.variantValidator.validate(variant, bindingResult);
		
		if(bindingResult.hasErrors()) { //Significa che la variant esiste oppure ci sono altri errori
			if(bindingResult.getAllErrors().toString().contains("variant.duplicata")) { 
				this.variantService.delete(variant);
				return "redirect:elencoVariant"; //Unico caso funzionante!
			}
			return "formRimuoviVariant.html"; //Ho problemi ma non il variant.duplicata, quindi lo user ha toppato
		}

		bindingResult.reject("variant.nonEsiste");
		return "formRimuoviVariant.html"; //Ha inserito una variant che non esiste
	}
	
	/*##############################################################*/
	/*##################/ADD TO VARIANT METHODS#####################*/
	/*##############################################################*/
	
	@GetMapping("/elencoAggiornaVariant")
	public String showElencoAggiornaVariant(Model model) {
		model.addAttribute("allVariants", this.variantService.findAllByOrderByNomeVariantAsc());
		return "elencoAggiornaVariant.html";
	}
	
	//Dentro elencoVariant fai un elenco con immagine e sotto il titolo, poi 3 a href con Visualizza, Aggiorna Editore, Aggiorna Manga
	@GetMapping("/impostaEditoreAVariant/{idVariant}")
	public String showAggiungiEditoreAVariant(@PathVariable("idVariant") Long idVariant, Model model) {
		Variant variantDaModificare = this.variantService.findById(idVariant);
		
		return "";
	}
	
	@GetMapping("/impostaEditoreAVariant/{idVariant}/{idEditore}")
	
	/*##############################################################*/
	/*######################/SUPPORT METHODS########################*/
	/*##############################################################*/
	
	private void addElencoNomiAndNazionalitàEditoriToModel(Model model) {
		//============================================================
		//Add the editori attributes for menu a tendina
		Set<String> elencoNomeEditori = new TreeSet<>(); //No dups
		Set<String> elencoNazionalitaEditori = new TreeSet<>(); //No dups

		for(Editore e : this.editoreService.findAllByOrderByNomeAsc()) {
			elencoNomeEditori.add(e.getNome());
			elencoNazionalitaEditori.add(e.getNazione());
		}

		model.addAttribute("elencoNomeEditori", elencoNomeEditori);
		model.addAttribute("elencoNazionalitaEditori", elencoNazionalitaEditori);
		//============================================================
	}

	private void addElencoTitoloAndAutoremangaToModel(Model model) {
		//============================================================
		//Add the manga attributes for menu a tendina
		Set<String> elencoTitoloManga = new TreeSet<>();
		Set<String> elencoAutoreManga = new TreeSet<>();

		for(Manga m : this.mangaService.findAllByOrderByTitoloAsc()) {
			elencoTitoloManga.add(m.getTitolo());
			elencoAutoreManga.add(m.getAutore());
		}

		model.addAttribute("elencoTitoloManga", elencoTitoloManga);
		model.addAttribute("elencoAutoreManga", elencoAutoreManga);
		//============================================================
	}

}
