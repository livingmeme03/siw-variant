package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.service.EditoreService;

@Component
public class EditoreValidator implements Validator {

	/*##############################################################*/
	/*#########################SERVICES#############################*/
	/*##############################################################*/
	
	@Autowired
	private EditoreService editoreService;

	/*##############################################################*/
	/*#########################VALIDATE#############################*/
	/*##############################################################*/

	@Override
	public void validate(Object o, Errors errors) {

		Editore editore = (Editore) o;
		
		this.modifyNazioneString(editore);
		
		if(editore.getNome()!=null && editore.getNazione()!=null 
				&& this.editoreService.existsByNomeAndNazione(editore.getNome(), editore.getNazione())) {
			errors.reject("editore.duplicato");
		}

	}

	/*##############################################################*/
	/*##################VALIDATE SUPPORT METHODS####################*/
	/*##############################################################*/
	
	private String modifyStringFirstUppercaseThenLowercase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        // Convert first letter to uppercase and the rest to lowercase
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	private void modifyNazioneString(Editore e) {
		e.setNazione(this.modifyStringFirstUppercaseThenLowercase(e.getNazione()));
	}
	
	
	/*##############################################################*/
	/*###########################SUPPORTS###########################*/
	/*##############################################################*/
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Editore.class.equals(aClass);
	}



}
