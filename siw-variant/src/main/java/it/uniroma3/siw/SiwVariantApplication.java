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
	//------------------------------------------------------------------------------------
	//1. nome in alto a dx
	//2. gestione errore login e reindirizzamento quando si clicca su risorsa proibita
	//3. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti editori manga e variant)
	//4. css
	//5. annotazioni transactional
	//6. optional: ricerca variant per effetto copertina (form grossa?)
	//7. optional: deploy su cloud
	//8. optional: annotazioni internazionali per i messaggi di errore
	
	// FATTO 1. aggiungi variant a un manga (addactorstomovie)
	// FATTO 2. aggiungi variant a un editore (addactorstomovie)
	// FATTO 3. ricerca manga per titolo
	// FATTO 4. ricerca manga per autore
	// FATTO 5. ricerca editore per nome
	// FATTO 6. ricerca editore per nazionalità?
	// FATTO 7. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	// FATTO 8. editori possono modificare/cancellare SOLO LE PROPRIE VARIANT
	// FATTO 11. rest

}
