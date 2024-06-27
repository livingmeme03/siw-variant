package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Manga;

public interface MangaRepository extends CrudRepository<Manga, Long>{

	public boolean existsByTitoloAndAutore(String titolo, String autore);

}
