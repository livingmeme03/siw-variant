package it.uniroma3.siw.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Manga {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String titolo;
	//@ManyToOne
	private Autore autore;
	private boolean ongoing;
	private int numeroVolumi;
}
