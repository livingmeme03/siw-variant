package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.repository.MangaRepository;
import jakarta.validation.Valid;

@Service
public class MangaService {
	
	/*#######################################################################################*/
	/*--------------------------------------REPOSITORY---------------------------------------*/
	/*#######################################################################################*/
	
	@Autowired
	private MangaRepository mangaRepository;

	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	
	public Iterable<Manga> findAll() {
		return this.mangaRepository.findAll();
	}


	public Manga findById(Long id) {
		try {
			return this.mangaRepository.findById(id).get();
		}
		catch (Exception e){
			return null;
		}	
	}
	
	public Manga save(Manga manga) {
		return this.mangaRepository.save(manga);
	}


	public boolean existsByTitoloAndAutore(String titolo, String autore) {
		return this.mangaRepository.existsByTitoloAndAutore(titolo, autore);
	}
	
	public Manga findByTitoloAndAutore(String titolo, String autore) {
		return this.mangaRepository.findByTitoloAndAutore(titolo, autore);
	}
	
	public Iterable<Manga> findAllByTitolo(String titolo) {
		return this.mangaRepository.findAllByTitolo(titolo);
	}
	
	public Iterable<Manga> findAllByAutore(String autore) {
		return this.mangaRepository.findAllByAutore(autore);	
	}


	public void delete(Manga manga) {
		Manga del = this.mangaRepository.findByTitoloAndAutore(manga.getTitolo(), manga.getAutore());
		this.mangaRepository.delete(del);
	}


	public Iterable<Manga> findAllByOrderByTitoloAsc() {
		return this.mangaRepository.findAllByOrderByTitoloAsc();	
	}
	
}
