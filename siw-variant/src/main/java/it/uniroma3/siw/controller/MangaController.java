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
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.service.MangaService;
import jakarta.validation.Valid;

@Controller
public class MangaController {

	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/

	@Autowired
	private MangaService mangaService;

	@Autowired
	private MangaValidator mangaValidator;

	//======================================================================\\
	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
	//======================================================================\\

	/*##############################################################*/
	/*########################/SHOW METHODS#########################*/
	/*##############################################################*/

	@GetMapping("/elencoManga")
	public String showElencoManga(Model model) {
		Iterable<Manga> allMangas = this.mangaService.findAll();
		model.addAttribute("allMangas", allMangas);
		return "elencoManga.html";
	}

	@GetMapping("/manga/{id}")
	public String showManga(@PathVariable("id") Long id, Model model) {
		model.addAttribute("manga", this.mangaService.findById(id));
		return "manga.html";
	}

	/*##############################################################*/
	/*######################/INSERT METHODS#########################*/
	/*##############################################################*/

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

}
