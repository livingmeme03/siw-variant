package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
	
	/*#######################################################################################*/
	/*-------------------------------------VARIABLES-----------------------------------------*/
	/*#######################################################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeUtente;
	
	@NotBlank
	private String cognome;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Editore editore;
	
	/*#######################################################################################*/
	/*--------------------------------GETTERS AND SETTERS------------------------------------*/
	/*#######################################################################################*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeUtente() {
		return nomeUtente; 
	}
	public void setNomeUtente(String nome) {
		this.nomeUtente = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public Editore getEditore() {
		return editore;
	}
	public void setEditore(Editore editore) {
		this.editore = editore;
	}
	
	/*#######################################################################################*/
	/*------------------------------------CLASS METHODS--------------------------------------*/
	/*#######################################################################################*/
	
}
