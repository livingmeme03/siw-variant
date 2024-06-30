package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {

	/*#######################################################################################*/
	/*-------------------------------------SERVICES------------------------------------------*/
	/*#######################################################################################*/

	@Autowired
	private CredentialsService credentialsService;
	
	/*#######################################################################################*/
	/*--------------------------------------VALIDATE-----------------------------------------*/
	/*#######################################################################################*/

	@Override
	public void validate(Object o, Errors errors) {

		Credentials credentials = (Credentials) o;
		//USERNAME UNICO
		if(credentials.getUsername()!=null
				&& this.credentialsService.existsByUsername(credentials.getUsername())) {
			System.out.println("\nCred duplicate!\n");
			errors.reject("credentials.duplicato");
		}
	}

	/*#######################################################################################*/
	/*-------------------------------VALIDATE SUPPORT METHODS--------------------------------*/
	/*#######################################################################################*/


	/*--------------------------------------SUPPORTS-----------------------------------------*/

	@Override
	public boolean supports(Class<?> aClass) {
		return CredentialsValidator.class.equals(aClass);
	}

}
