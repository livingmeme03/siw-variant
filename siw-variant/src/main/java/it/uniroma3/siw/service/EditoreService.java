package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.repository.EditoreRepository;
import jakarta.validation.Valid;

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
	
	public Editore findByNomeAndNazione(String nome, String nazione) {
		return this.editoreRepository.findByNomeAndNazione(nome, nazione);
	}

	public void delete(Editore editore) {
		Editore del = this.editoreRepository.findByNomeAndNazione(editore.getNome(), editore.getNazione());
		this.editoreRepository.delete(del);
	}
	
}
