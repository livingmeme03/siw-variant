package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Autore {
	
	/*##############################################################*/
	/*#########################VARIABLES############################*/
	/*##############################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@NotNull
	@Past
	private LocalDate dataNascita;
	
	@NotBlank
	private String nazionalità;
//	@OneToMany(mappedBy="autore")
//	private List<Manga> opere;
	
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
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getNazionalità() {
		return nazionalità;
	}
	public void setNazionalità(String nazionalità) {
		this.nazionalità = nazionalità;
	}
	
	/*##############################################################*/
	/*#####################EQUALS, HASHCODE#########################*/
	/*##############################################################*/
	
	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, nazionalità, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(nazionalità, other.nazionalità) && Objects.equals(nome, other.nome);
	}
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/

}
