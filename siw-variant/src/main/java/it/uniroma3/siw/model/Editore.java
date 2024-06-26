package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Editore {
	
	/*##############################################################*/
	/*#########################VARIABLES############################*/
	/*##############################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	private String pathImmagine;
	
	@NotBlank
	private String nazione;
	
	@OneToMany(mappedBy = "editore", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<Variant> variantPubblicate;
	
	/*##############################################################*/
	/*####################GETTERS AND SETTERS#######################*/
	/*##############################################################*/
	
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
	
	/*##############################################################*/
	/*#####################EQUALS, HASHCODE#########################*/
	/*##############################################################*/
	
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
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/
	
	
}
