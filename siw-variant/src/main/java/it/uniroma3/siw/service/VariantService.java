package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

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
		Variant del = this.variantRepository.findByNomeVariantAndVolume(variant.getNomeVariant(), variant.getVolume());
		this.variantRepository.delete(del);
	}

	public Iterable<Variant> findAllByOrderByMangaTitolo() {
		return this.variantRepository.findAllByOrderByMangaTitolo();
	}
	
	public Iterable<Variant> findAllByOrderByNomeVariantAsc() {
		return this.variantRepository.findAllByOrderByNomeVariantAsc();
	}

	public boolean existsByNomeVariant(String nomeVariant) {
		return this.variantRepository.existsByNomeVariant(nomeVariant);
	}

	public boolean existsByNomeVariantAndVolume(String nomeVariant, Integer volume) {
		return this.variantRepository.existsByNomeVariantAndVolume(nomeVariant, volume);
	}

	public List<Variant> findByEffettoCopertina(String effettoCopertina) {
		return this.variantRepository.findByEffettoCopertina(effettoCopertina);
	}

	public Iterable<Variant> findByNomeVariant(String nomeVariant) {
		return this.variantRepository.findByNomeVariant(nomeVariant);
	}

	public Iterable<Variant> findAllByManga(Manga manga) {
		return this.variantRepository.findAllByManga(manga);
	}
	
}
