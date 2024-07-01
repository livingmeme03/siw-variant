package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
@Entity
public class Editore {
	
	/*#######################################################################################*/
	/*-------------------------------------VARIABLES-----------------------------------------*/
	/*#######################################################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@Column(nullable = true)
	private String pathImmagine;
	
	@NotBlank
	private String nazione;
	
	@OneToMany(mappedBy = "editore", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<Variant> variantPubblicate;
	
	
	/*#######################################################################################*/
	/*--------------------------------GETTERS AND SETTERS------------------------------------*/
	/*#######################################################################################*/
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public List<Variant> getVariantPubblicate() {
		return variantPubblicate;
	}
	public void setVariantPubblicate(List<Variant> variantPubblicate) {
		this.variantPubblicate = variantPubblicate;
	}
	
	/*#######################################################################################*/
	/*--------------------------------EQUALS AND HASHCODE------------------------------------*/
	/*#######################################################################################*/
	
	@Override
	public int hashCode() {
		return Objects.hash(nazione, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editore other = (Editore) obj;
		return Objects.equals(nazione, other.nazione) && Objects.equals(nome, other.nome);
	}

	/*#######################################################################################*/
	/*---------------------------------------TO STRING---------------------------------------*/
	/*#######################################################################################*/
	
	@Override
	public String toString() {
		return "Editore [id=" + id + ", nome=" + nome + ", pathImmagine=" + pathImmagine + ", nazione=" + nazione
				+ ", variantPubblicate=" + variantPubblicate + "]";
	}
	
	/*#######################################################################################*/
	/*------------------------------------CLASS METHODS--------------------------------------*/
	/*#######################################################################################*/
}
