package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Manga {
	
	/*##############################################################*/
	/*#########################VARIABLES############################*/
	/*##############################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String titolo;
	
	@NotNull
	@ManyToOne
	private Autore autore;
	
	private boolean ongoing;
	
	@Min(1)
	private int numeroVolumi;
	
	@OneToMany(mappedBy = "manga", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<Variant> variants;
	
	/*##############################################################*/
	/*####################GETTERS AND SETTERS#######################*/
	/*##############################################################*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Autore getAutore() {
		return autore;
	}
	public void setAutore(Autore autore) {
		this.autore = autore;
	}
	public boolean isOngoing() {
		return ongoing;
	}
	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}
	public int getNumeroVolumi() {
		return numeroVolumi;
	}
	public void setNumeroVolumi(int numeroVolumi) {
		this.numeroVolumi = numeroVolumi;
	}
	
	/*##############################################################*/
	/*#####################EQUALS, HASHCODE#########################*/
	/*##############################################################*/
	
	@Override
	public int hashCode() {
		return Objects.hash(autore, titolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manga other = (Manga) obj;
		return Objects.equals(autore, other.autore) && Objects.equals(titolo, other.titolo);
	}
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/
	
	
}
