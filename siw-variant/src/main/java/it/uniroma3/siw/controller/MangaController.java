package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.service.MangaService;

@Controller
public class MangaController {

	/*##############################################################*/
	/*##########################SERVICES############################*/
	/*##############################################################*/

	@Autowired
	private MangaService mangaService;

	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
//======================================================================\\

	/*##############################################################*/
	/*############################/manga############################*/
	/*##############################################################*/

	@GetMapping("/elencoManga")
	public String showElencoManga(Model model) {
		Iterable<Manga> allMangas = this.mangaService.findAll();
		model.addAttribute("allMangas", allMangas);
		return "elencoManga.html";
	}

	@GetMapping("/manga/{id}")
	public String showManga(Model model) {
		
		return "manga.html";
	}

}
