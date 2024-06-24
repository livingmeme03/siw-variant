package it.uniroma3.siw.model;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Variant {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private LocalDate dataUscita;
	private String pathImmagine;
	private String descrizione;
	private int rarità;
	private String effettoCopertina;
	//@ManyToOne 
	private Editore editore;
	//@ManyToOne
	private Manga manga;
	private String lingua;
	
}
