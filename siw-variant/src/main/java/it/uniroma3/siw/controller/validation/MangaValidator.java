package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.service.MangaService;

@Component
public class MangaValidator implements Validator{
	
	@Autowired
	private MangaService mangaService;


	@Override
	public void validate(Object o, Errors errors) {
		Manga manga = (Manga) o;
		String pathImg = manga.getPathImmagine();
		if(pathImg !=null && pathImg.contains("../")) {
			errors.reject("aiuto.cihackerano.pathtraversal");
		}
		if(manga.getTitolo()!=null && manga.getAutore()!=null 
				&& this.mangaService.existsByTitoloAndAutore(manga.getTitolo(), manga.getAutore())) {
			errors.reject("manga.duplicato");
		}
		if(manga.getOngoing()!=null
				&& !(manga.getOngoing().equals("In corso") || manga.getOngoing().equals("Terminato"))) {
			System.out.println(manga.getOngoing());
			errors.reject("manga.statoNonValido");
		}
		
		
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Manga.class.equals(aClass);
	}

}
