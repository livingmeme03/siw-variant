package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.EditoreService;
import it.uniroma3.siw.service.VariantService;
import jakarta.validation.Valid;

@Controller
public class EditoreController {

	/*#######################################################################################*/
	/*---------------------------------------SERVICES----------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private EditoreService editoreService;
	
	@Autowired
	private VariantService variantService;
	
	/*#######################################################################################*/
	/*---------------------------------------VALIDATOR---------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private EditoreValidator editoreValidator;

	//=======================================================================================\\
	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	//========================================================================================\\

	/*#######################################################################################*/
	/*-------------------------------------SHOW METHODS--------------------------------------*/
	/*#######################################################################################*/

	//Per tutti
	@GetMapping("/elencoEditori")
	public String showElencoEditori(Model model) {
		Iterable<Editore> allEditori = this.editoreService.findAllByOrderByNomeAsc();
		model.addAttribute("allEditori", allEditori);
		return "elencoEditori.html";
	}

	//Per tutti
	@GetMapping("/editore/{id}")
	public String showEditore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("editore", this.editoreService.findById(id));
		//model.addAttribute("listaVariant", this.editoreService.findById(id).getVariantPubblicatePubblicate());
		return "editore.html";
	}
	
	//Per admin
	@GetMapping("/admin/impostaEditoreAVariant/{idVariant}")
	public String showAggiungiEditoreAVariant(@PathVariable("idVariant") Long idVariant, Model model) {		
		Iterable<Editore> allEditori = this.editoreService.findAllByOrderByNomeAsc();
		model.addAttribute("allEditori", allEditori);
		model.addAttribute("idVariant", idVariant);
		return "/admin/elencoEditoriPerInserireInVariant.html";
	}

	/*#######################################################################################*/
	/*------------------------------------INSERT METHODS-------------------------------------*/
	/*#######################################################################################*/

	//Per admin
	@GetMapping("/admin/aggiungiEditore")
	public String showFormAggiungiEditore(Model model) {
		model.addAttribute("nuovoEditore", new Editore());
		return "/admin/formAggiungiEditore.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiEditore")
	public String newEditore(@Valid @ModelAttribute("nuovoEditore") Editore editore, BindingResult bindingResult, Model model) {
		this.editoreValidator.validate(editore, bindingResult);
		if(bindingResult.hasErrors()) {
			return "/admin/formAggiungiEditore.html";
		}
		else {
			this.editoreService.save(editore);
			return "redirect:/editore/"+editore.getId();
		}
	}
	
	/*#######################################################################################*/
	/*------------------------------------REMOVE METHODS-------------------------------------*/
	/*#######################################################################################*/
	
	//Per admin
	@GetMapping("/admin/rimuoviEditore")
	public String showFormRimuoviEditore(Model model) {
		model.addAttribute("elencoEditori", this.editoreService.findAllByOrderByNomeAsc());
		return "/admin/elencoRimuoviEditori.html";
	}
	
	//Per admin
	@GetMapping("/admin/rimuoviEditore/{idEditore}")
	public String rimuoviEditore(Model model, @PathVariable Long idEditore) {

		Editore editoreDaRimuovere = this.editoreService.findById(idEditore);
		if(editoreDaRimuovere != null) {
			this.editoreService.delete(editoreDaRimuovere);
		}
		return "redirect:/elencoEditori";
	}
	
	
	/*#######################################################################################*/
	/*-------------------------------MODIFICA VARIANT EDITORE--------------------------------*/
	/*#######################################################################################*/
	
	
	//Per admin
	@GetMapping("/admin/modificaVariantEditore")
	public String showElencoEditoriPerModificareVariant(Model model) {
		Iterable<Editore> allEditori = this.editoreService.findAllByOrderByNomeAsc();
		model.addAttribute("allEditori", allEditori);
		return "/admin/elencoAggiornaEditori.html";
	}
	
	//Per admin
	@GetMapping("/admin/modificaVariantEditore/{editoreId}")
	public String showModificaVariantEditore(@PathVariable("editoreId") Long editoreId, Model model) {
		Editore editore = this.editoreService.findById(editoreId);
		
		if(editore==null) {		
			return "redirect:/admin/modificaVariantEditore"; 	//Non metto errori, non modello per persone che giocano con gli url...
		}
		
		List<Variant> allVariantMesse = new ArrayList<>(editore.getVariantPubblicate()); //La lista delle variant presenti nell'editore
		
		List<Variant> allVariantDisponibili = (List<Variant>) this.variantService.findAll();
		
		allVariantDisponibili.removeAll(allVariantMesse);
		
		model.addAttribute("allVariantMesse", allVariantMesse);
		model.addAttribute("allVariantDisponibili", allVariantDisponibili);
		model.addAttribute("editore", editore);
		
		return "/admin/elencoVariantPerModificareEditore.html";
	}
	

	//-------------------------------------Aggiungi Variant a Editore-------------------------------------\\
	
	//Per admin
	@GetMapping("/admin/addVariant/{editoreId}/{variantId}")
	public String showModificaVariantEditoreAndAddVariant(@PathVariable("editoreId") Long editoreId, @PathVariable("variantId") Long variantId, Model model) {

		//Logica per aggiungere variant a editore
		Editore editore = this.editoreService.findById(editoreId);
		Variant variant = this.variantService.findById(variantId);
		
		if(editore==null) {
			return "redirect:/admin/modificaVariantEditore"; //Non metto errori, non modello per persone che giocano con gli url...
		}
		
		if(variant == null) {
			return "redirect:/admin/modificaVariantEditore/"+editoreId;
		}

		editore.getVariantPubblicate().add(variant);
		variant.setEditore(editore);

		this.editoreService.save(editore);
		this.variantService.save(variant);
		
		return "redirect:/admin/modificaVariantEditore/"+editoreId;
		
	}
	
	//-------------------------------------Rimuovi Variant da Editore-------------------------------------\\
	
	//Per admin
	@GetMapping("/admin/removeVariant/{editoreId}/{variantId}")
	public String showModificaVariantEditoreAndRemoveVariant(@PathVariable("editoreId") Long editoreId, @PathVariable("variantId") Long variantId, Model model) {

		//Logica per aggiungere variant a editore
		Editore editore = this.editoreService.findById(editoreId);
		Variant variant = this.variantService.findById(variantId);
	
		
		if(editore==null || !editore.getVariantPubblicate().contains(variant)) {
			return "redirect:/admin/modificaVariantEditore"; //Non metto errori, non modello per persone che giocano con gli url...
		}

		editore.getVariantPubblicate().remove(variant);
		variant.setEditore(null);

		this.editoreService.save(editore);
		this.variantService.save(variant);
		
		return "redirect:/admin/modificaVariantEditore/"+editoreId;
	}

	
	/*#######################################################################################*/
	/*----------------------------------------RICERCA----------------------------------------*/
	/*#######################################################################################*/
	//Tutto per tutti
	
	
	
	@GetMapping("/ricercaEditorePerNome")
	public String showFormRicercaEditoreNome(Model model) {
		model.addAttribute("editoreInfos", new Editore());
		return "formCercaEditoreNome.html";
	}
	
	
	@PostMapping("/ricercaEditorePerNome")
	public String showEditoreConStessoNome(@Valid @ModelAttribute("editoreInfos") Editore editore,
					BindingResult bindingResult, Model model) {
		
		Iterable<Editore> allEditori = this.editoreService.findAllByNome(editore.getNome()); //Non univoco
		
		model.addAttribute("allEditori", allEditori);
		return "elencoEditori.html"; //Un modo carino per sfruttare il template dell' editore per un editore che non esiste
	}
	
	@GetMapping("/ricercaEditorePerNazione")
	public String showFormRicercaEditoreNazione(Model model) {
		model.addAttribute("editoreInfos", new Editore());
		return "formCercaEditoreNazione.html";
	}
	
	
	@PostMapping("/ricercaEditorePerNazione")
	public String showEditoreConStessoNomeNazione(@Valid @ModelAttribute("editoreInfos") Editore editore,
					BindingResult bindingResult, Model model) {
		
		Iterable<Editore> allEditori = this.editoreService.findAllByNazione(editore.getNazione()); //Non univoco
			
		model.addAttribute("allEditori", allEditori);
		return "elencoEditori.html"; //Un modo carino per sfruttare il template dell' editore per un editore che non esiste
	}
	
	
}