package it.uniroma3.siw.controller.validation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Editore;
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

	/*##############################################################*/
	/*#########################VALIDATE#############################*/
	/*##############################################################*/

	@Override
	public void validate(Object o, Errors errors) {
		Variant variant = (Variant) o;
		
		//Verifica duplicati
		if(variant.getNomeVariant()!=null && this.variantService.existsByNomeVariant(variant.getNomeVariant())) {
			errors.reject("variant.duplicata");
		}
		
	}

	/*##############################################################*/
	/*##################VALIDATE SUPPORT METHODS####################*/
	/*##############################################################*/


	/*##############################################################*/
	/*###########################SUPPORTS###########################*/
	/*##############################################################*/

	@Override
	public boolean supports(Class<?> clazz) {
		return Variant.class.equals(clazz);
	}

}
