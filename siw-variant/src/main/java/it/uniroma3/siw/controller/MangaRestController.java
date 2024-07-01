package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import it.uniroma3.siw.model.Manga;

import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.MangaService;

@RestController
public class MangaRestController {

	@Autowired
	private MangaService mangaService;
	
	@GetMapping("/rest/manga/{id}")
	public Manga getManga(@PathVariable("id") Long id) {
		return this.mangaService.findById(id);
	}
	
	@GetMapping("/rest/elencoManga")
	public List<Manga> getElencoManga() {
		return (List<Manga>)this.mangaService.findAll();
	}
}
