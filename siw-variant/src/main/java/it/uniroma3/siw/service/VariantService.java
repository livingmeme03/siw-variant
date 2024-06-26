package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.repository.VariantRepository;

@Service
public class VariantService {
	
	/*##############################################################*/
	/*#########################REPOSITORY###########################*/
	/*##############################################################*/
	
	@Autowired
	private VariantRepository variantRepository;

	/*##############################################################*/
	/*###########################METHODS############################*/
	/*##############################################################*/

	public Iterable<Variant> findAll() {
		return this.variantRepository.findAll();
	}

	public Variant findById(Long id) {	
		return this.variantRepository.findById(id).get();
	}
	
}
