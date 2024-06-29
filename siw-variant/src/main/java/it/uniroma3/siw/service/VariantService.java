package it.uniroma3.siw.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.repository.VariantRepository;
import jakarta.validation.Valid;

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
	
	public Variant save(Variant variant) {
		return this.variantRepository.save(variant);
	}
	
	public boolean existsByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(LocalDate dataUscita, Manga manga, Editore editore, Integer volume, String effettoCopertina) {
		return this.variantRepository.existsByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(dataUscita, manga, editore, volume, effettoCopertina);
	}

	public void delete(Variant variant) {
		Variant del = this.variantRepository.findByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(variant.getDataUscita(), 
																	variant.getManga(), variant.getEditore(), variant.getVolume(), variant.getEffettoCopertina());
		del = this.variantRepository.findById(new Long(52)).get();
		this.variantRepository.delete(del);
		System.out.println(del.toString());
	}
	
	
}
