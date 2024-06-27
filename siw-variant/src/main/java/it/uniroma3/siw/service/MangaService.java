package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.repository.MangaRepository;

@Service
public class MangaService {
	
	/*##############################################################*/
	/*#########################REPOSITORY###########################*/
	/*##############################################################*/
	
	@Autowired
	private MangaRepository mangaRepository;


	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
	
	public Iterable<Manga> findAll() {
		return this.mangaRepository.findAll();
	}


	public Manga findById(Long id) {
		return this.mangaRepository.findById(id).get();
	}
	
	public Manga save(Manga manga) {
		return this.mangaRepository.save(manga);
	}


	public boolean existsByTitoloAndAutore(String titolo, String autore) {
		return this.mangaRepository.existsByTitoloAndAutore(titolo, autore);
	}
	
}
