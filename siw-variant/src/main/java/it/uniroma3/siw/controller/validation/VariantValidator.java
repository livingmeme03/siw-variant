package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.EditoreService;
import it.uniroma3.siw.service.MangaService;
import it.uniroma3.siw.service.VariantService;

@Component
public class VariantValidator implements Validator{

	/*##############################################################*/
	/*#########################SERVICES#############################*/
	/*##############################################################*/
	
	@Autowired
	private VariantService variantService;
	
	@Autowired
	private MangaService mangaService;
	
	@Autowired
	private EditoreService editoreService;
	
	/*##############################################################*/
	/*#########################VALIDATE#############################*/
	/*##############################################################*/
	
	@Override
	public void validate(Object o, Errors errors) {
		Variant variant = (Variant) o;

		//this.checkVolumeTooBig(variant, errors);
		
		if(variant.getDataUscita()!=null && variant.getEditore()!=null && variant.getManga()!=null && variant.getVolume()!=null && variant.getEffettoCopertina()!=null
				&& this.variantService.existsByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(variant.getDataUscita(), variant.getManga(), variant.getEditore(), 
						variant.getVolume(), variant.getEffettoCopertina())) {
			errors.reject("variant.duplicata");
		}
		if(variant.getManga()!=null && !this.mangaService.existsByTitoloAndAutore(variant.getManga().getTitolo(), variant.getManga().getAutore())) {
			errors.reject("variant.mangaNonEsiste");
		}
		if(variant.getEditore()!=null && !this.editoreService.existsByNomeAndNazione(variant.getEditore().getNome(), variant.getEditore().getNazione())) {
			errors.reject("variant.editoreNonEsiste");
		}
		
			
		//TODO: controlla duplicati
	}

	/*##############################################################*/
	/*##################VALIDATE SUPPORT METHODS####################*/
	/*##############################################################*/
	
	//Il volume della variant non deve superare il numero di volumi del manga
	private void checkVolumeTooBig(Variant variant, Errors errors) {

		Manga mangaRelativo = variant.getManga();

		int numeroVolumiMax = mangaRelativo.getNumeroVolumi();

		if(variant.getVolume() > numeroVolumiMax) { //Bad!
			//Il manga ha 30 volumi e la variant si riferisce al vol 31
			errors.reject("variant.volumeTooBig"); 
		}
	}
	
	
	
	
	
	
	/*##############################################################*/
	/*###########################SUPPORTS###########################*/
	/*##############################################################*/
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Variant.class.equals(clazz);
	}
	
}
