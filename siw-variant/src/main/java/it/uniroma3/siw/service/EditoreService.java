package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.repository.EditoreRepository;

@Service
public class EditoreService {
	
	/*##############################################################*/
	/*#########################REPOSITORY###########################*/
	/*##############################################################*/
	
	@Autowired
	private EditoreRepository editoreRepository;

	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/
	
	public Iterable<Editore> findAll() {
		return this.editoreRepository.findAll();
	}

	public Editore findById(Long id) {
		return this.editoreRepository.findById(id).get();
	}

	public Editore save(Editore editore) {
		return this.editoreRepository.save(editore);
	}
	
	public boolean existsByNomeAndNazione(String nome, String nazione) {
		return this.editoreRepository.existsByNomeAndNazione(nome, nazione);
	}

	
}
