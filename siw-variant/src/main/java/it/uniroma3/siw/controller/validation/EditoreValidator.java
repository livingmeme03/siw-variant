package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.service.EditoreService;

@Component
public class EditoreValidator implements Validator{

	@Autowired
	private EditoreService editoreService;



	@Override
	public void validate(Object o, Errors errors) {

		Editore editore = (Editore) o;
		String pathImg = editore.getPathImmagine();
		if(pathImg !=null && pathImg.contains("../")) {
			errors.reject("aiuto.cihackerano.pathtraversal");
		}
		if(editore.getNome()!=null && editore.getNazione()!=null 
				&& this.editoreService.existsByNomeAndNazione(editore.getNome(), editore.getNazione())) {
			errors.reject("editore.duplicato");
		}

	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Editore.class.equals(aClass);
	}



}
