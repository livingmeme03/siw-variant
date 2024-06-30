package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;

public interface VariantRepository extends CrudRepository<Variant, Long>{

	public boolean existsByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(LocalDate dataUscita, Manga manga, Editore editore, Integer volume, String effettoCopertina);

	public Variant findByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(LocalDate dataUscita, Manga manga, Editore editore, Integer volume, String effettoCopertina);

	public Iterable<Variant> findAllByOrderByMangaTitolo();

	public Iterable<Variant> findAllByOrderByNomeVariantAsc();
	
	public boolean existsByNomeVariant(String nomeVariant);

	public List<Variant> findByNomeVariant(String nomeVariant);

	public boolean existsByNomeVariantAndVolume(String nomeVariant, Integer volume);

	public Variant findByNomeVariantAndVolume(String nomeVariant, Integer volume);

	public List<Variant> findByEffettoCopertina(String effettoCopertina);

	public Iterable<Variant> findAllByManga(Manga manga);

	
}
