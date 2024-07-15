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
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	private AuthenticationController authenticationController;

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

	//Per tutti
	@GetMapping("/elencoManga")
	public String showElencoManga(Model model) {
		Iterable<Manga> allMangas = this.mangaService.findAllByOrderByTitoloAsc();
		model.addAttribute("allMangas", allMangas);
		return "elencoManga.html";
	}

	//Per tutti
	@GetMapping("/manga/{id}")
	public String showManga(@PathVariable("id") Long id, Model model) {
		model.addAttribute("manga", this.mangaService.findById(id));
		return "manga.html";
	}

	//Per admin
	@GetMapping("/admin/impostaMangaAVariant/{idVariant}")
	public String showAggiungiMangaAVariantAdmin(@PathVariable("idVariant") Long idVariant, Model model) {
		Iterable<Manga> allMangas = this.mangaService.findAllByOrderByTitoloAsc();
		model.addAttribute("allMangas", allMangas);
		if(this.variantService.findById(idVariant)==null) {
			return "redirect:/elencoAggiornaVariant";
		}
		model.addAttribute("idVariant", idVariant);
		return "/admin/elencoMangaPerInserireInVariant.html";
	}

	//Per editore
	@GetMapping("/impostaMangaAVariant/{idVariant}")
	public String showAggiungiMangaAVariantEditore(@PathVariable("idVariant") Long idVariant, Model model) {
		Variant variant = this.variantService.findById(idVariant);
		if(variant==null) {
			return "redirect:/elencoAggiornaVariant";
		}
		Editore editoreVariantDaModificare = variant.getEditore();				//controllo che l'utente non mi metta
		Editore curr = this.authenticationController.getEditoreSessioneCorrente();//l'id di una variant non sua nel'URL
		if(curr.equals(editoreVariantDaModificare)) {							
			Iterable<Manga> allMangas = this.mangaService.findAllByOrderByTitoloAsc();
			model.addAttribute("allMangas", allMangas);
			model.addAttribute("idVariant", idVariant);
			return "elencoMangaPerInserireInVariant.html";
		}
		return "redirect:/elencoAggiornaVariant";
	}

	/*#######################################################################################*/
	/*------------------------------------INSERT METHODS-------------------------------------*/
	/*#######################################################################################*/

	//Per admin
	@GetMapping("/admin/aggiungiManga")
	public String showFormAggiungiManga(Model model) {
		model.addAttribute("nuovoManga", new Manga());
		return "/admin/formAggiungiManga.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiManga")
	public String newManga(@Valid @ModelAttribute("nuovoManga") Manga manga, BindingResult bindingResult, Model model) {
		this.mangaValidator.validate(manga, bindingResult);
		if(bindingResult.hasErrors()) {
			return "/admin/formAggiungiManga.html";
		}
		else {
			this.mangaService.save(manga);
			return "redirect:/manga/"+manga.getId();
		}
	}

	/*#######################################################################################*/
	/*-----------------------------------SEARCH METHODS--------------------------------------*/
	/*#######################################################################################*/
	//Tutto per tutti

	@GetMapping("/ricercaMangaPerTitolo")
	public String showFormSearchMangaTitolo(Model model) {
		return "formCercaMangaTitolo.html";
	}

	@PostMapping("/ricercaMangaPerTitolo")
	public String showMangaTrovatiTitolo(Model model, @RequestParam String titolo) {
		model.addAttribute("elencoManga", this.mangaService.findAllByTitolo(titolo));
		return "/elencoMangaTrovatiPerTitolo.html";
	}

	@GetMapping("/ricercaMangaPerAutore")
	public String showFormSearchManga(Model model) {
		return "formCercaMangaAutore.html";
	}

	@PostMapping("/ricercaMangaPerAutore")
	public String showMangaTrovatiAutore(Model model, @RequestParam String autore) {
		model.addAttribute("elencoManga", this.mangaService.findAllByAutore(autore));
		return "/elencoMangaTrovatiPerAutore.html";
	}

	/*#######################################################################################*/
	/*-----------------------------------UPDATE METHODS--------------------------------------*/
	/*#######################################################################################*/

	//Per admin
	@GetMapping("/admin/elencoAggiornaManga")		//non servono validazioni 
	public String showElencoAggiornaManga(Model model) {
		model.addAttribute("elencoManga", this.mangaService.findAllByOrderByTitoloAsc());
		return "/admin/elencoAggiornaManga.html";
	}

	//Per admin
	@GetMapping("/admin/modificaVariantManga/{idManga}") 
	public String showModificaVariantManga(@PathVariable Long idManga, Model model) {
		if(this.mangaService.findById(idManga)==null) {
			return "redirect:/admin/elencoAggiornaManga";
		}
		List<Variant> variantDelManga = this.mangaService.findById(idManga).getVariants();
		List<Variant> variantDaAggiungere = (List<Variant>) this.variantService.findAllByOrderByNomeVariantAsc();

		variantDaAggiungere.removeAll(variantDelManga);

		model.addAttribute("variantDelManga", variantDelManga);
		model.addAttribute("variantDaAggiungere", variantDaAggiungere);
		model.addAttribute("manga", this.mangaService.findById(idManga));
		return "/admin/elencoVariantPerModificareManga.html";
	}

	//Per admin
	@GetMapping("/admin/aggiungiVariantAManga/{idManga}/{idVariant}")
	public String aggiungiVariantAManga(@PathVariable Long idManga, @PathVariable Long idVariant, Model model) {

		//Controlli perché il try catch nel findById permette di avere manga/variant che siano null
		Manga manga = this.mangaService.findById(idManga);
		Variant variant = this.variantService.findById(idVariant);
		if(manga==null) {
			return "redirect:/admin/elencoAggiornaManga";
		}
		if(variant!=null) {
			variant.setManga(manga);
			manga.getVariants().add(variant);
			this.variantService.save(variant);
			this.mangaService.save(manga);
		}
		return "redirect:/admin/modificaVariantManga/" + idManga;
	}

	//Per admin
	@GetMapping("/admin/rimuoviVariantDaManga/{idManga}/{idVariant}") 
	public String rimuoviVariantDaManga(@PathVariable Long idManga, @PathVariable Long idVariant, Model model) {
		
		//Controlli perché il try catch nel findById permette di avere manga/variant che siano null
		Variant variant = this.variantService.findById(idVariant);
		Manga manga = this.mangaService.findById(idManga);
		if(manga==null) {
			return "redirect:/admin/elencoAggiornaManga";
		}
		if(variant!=null) {
			variant.setManga(null);
			manga.getVariants().remove(variant);
			this.variantService.save(variant);
			this.mangaService.save(manga);
		}
		return "redirect:/admin/modificaVariantManga/" + idManga;
	}

	/*#######################################################################################*/
	/*------------------------------------REMOVE METHODS-------------------------------------*/
	/*#######################################################################################*/


	//Per admin
	@GetMapping("/admin/rimuoviManga")
	public String showFormRimuoviManga(Model model) {
		model.addAttribute("elencoManga", this.mangaService.findAllByOrderByTitoloAsc());
		return "/admin/elencoRimuoviManga.html";
	}

	//Per admin
	@GetMapping("/admin/rimuoviManga/{idManga}")
	public String rimuoviManga(Model model, @PathVariable Long idManga) {

		Manga mangaDaRimuovere = this.mangaService.findById(idManga);
		if(mangaDaRimuovere != null) {
			this.mangaService.delete(mangaDaRimuovere);
		}
		return "redirect:/elencoManga";
	}


}
