package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Manga {
	
	/*#######################################################################################*/
	/*-------------------------------------VARIABLES-----------------------------------------*/
	/*#######################################################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String titolo;
	
	@NotBlank
	private String autore;
	
	private String pathImmagine;

	@Column(nullable = true)
	private String ongoing;
	
	@Min(1)
	private Integer numeroVolumi;
	
	@OneToMany(mappedBy = "manga", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<Variant> variants;
	
	/*#######################################################################################*/
	/*--------------------------------GETTERS AND SETTERS------------------------------------*/
	/*#######################################################################################*/
	
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
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public void setOngoing(String ongoing) {
		this.ongoing = ongoing;
	}
	public Integer getNumeroVolumi() {
		return numeroVolumi;
	}
	public void setNumeroVolumi(Integer numeroVolumi) {
		this.numeroVolumi = numeroVolumi;
	}
	
	public String getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}
	public List<Variant> getVariants() {
		return variants;
	}
	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}
	public String getOngoing() {
		return ongoing;
	}
	
	/*#######################################################################################*/
	/*--------------------------------EQUALS AND HASHCODE------------------------------------*/
	/*#######################################################################################*/
	
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

	/*#######################################################################################*/
	/*---------------------------------------TO STRING---------------------------------------*/
	/*#######################################################################################*/
	
	@Override
	public String toString() {
		return "Manga [id=" + id + ", titolo=" + titolo + ", autore=" + autore + ", pathImmagine=" + pathImmagine
				+ ", ongoing=" + ongoing + ", numeroVolumi=" + numeroVolumi + ", variants=" + variants + "]";
	}
	
	/*#######################################################################################*/
	/*------------------------------------CLASS METHODS--------------------------------------*/
	/*#######################################################################################*/
}
