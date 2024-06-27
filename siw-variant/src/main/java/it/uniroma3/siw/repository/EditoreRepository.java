package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Editore;

public interface EditoreRepository extends CrudRepository<Editore, Long>{

	public boolean existsByNomeAndNazione(String nome, String nazione);
	
	public Editore findByNomeAndNazione(String nome, String nazione);
}
