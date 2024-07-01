package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwVariantApplication {
	
	/*##############################################################*/
	/*##########################MAIN APP############################*/
	/*##############################################################*/

	public static void main(String[] args) {
		SpringApplication.run(SiwVariantApplication.class, args);
	}
	
	//TODO
	//3. ricerca manga per titolo
	//4. ricerca manga per autore?
	//5. ricerca editore per nome
	//6. ricerca editore per nazionalità?
	//------------------------------------------------------------------------------------
	//7. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	//8. editori possono modificare/cancellare SOLO LE PROPRIE VARIANT
	//9. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti editori manga e variant)
	//10. css
	//11. rest
	//12. annotazioni transactional
	//13. optional: ricerca variant per effetto copertina (form grossa?)
	//14. optional: deploy su cloud
	//15. optional: annotazioni internazionali per i messaggi di errore
	
	// FATTO 1. aggiungi variant a un manga (addactorstomovie)
	// FATTO 2. aggiungi variant a un editore (addactorstomovie)

}
