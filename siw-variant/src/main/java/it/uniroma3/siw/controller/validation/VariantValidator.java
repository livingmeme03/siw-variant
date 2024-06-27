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

		Editore editore = variant.getEditore();
		Manga manga = variant.getManga();

		//Verifica duplicati
		if(variant.getDataUscita()!=null && editore!=null && manga!=null && variant.getVolume()!=null && variant.getEffettoCopertina()!=null
				&& this.variantService.existsByDataUscitaAndMangaAndEditoreAndVolumeAndEffettoCopertina(variant.getDataUscita(), manga, editore, 
						variant.getVolume(), variant.getEffettoCopertina())) {
			errors.reject("variant.duplicata");
		}

		if(manga==null) {
			errors.reject("variant.mangaNonEsiste");
		}
		if(editore==null) {
			errors.reject("variant.editoreNonEsiste");
		}

		if(variant.getPathImmagine() !=null && variant.getPathImmagine().contains("../")) {
			errors.reject("aiuto.cihackerano.pathtraversal");
		}
		this.checkVolumeTooBig(variant, errors);
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
