package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Manga;
import it.uniroma3.siw.model.Variant;
import it.uniroma3.siw.service.VariantService;

public class VariantValidator implements Validator{

	/*##############################################################*/
	/*#########################SERVICES#############################*/
	/*##############################################################*/
	
	@Autowired
	private VariantService variantService;
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/
	
	@Override
	public void validate(Object o, Errors errors) {
		Variant variant = (Variant) o;

		this.checkVolumeTooBig(variant, errors);
		
		//TODO: controlla duplicati
	}

	//###########################################################################
	//Il volume della variant non deve superare il numero di volumi del manga
	//###########################################################################
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
