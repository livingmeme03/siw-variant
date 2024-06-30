package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Variant {
	
	/*##############################################################*/
	/*#########################VARIABLES############################*/
	/*##############################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeVariant;

	private LocalDate dataUscita;
	
	@Column(nullable = true)
	private String pathImmagine;
	
	@Column(nullable = true)
	private String descrizione;
	
	@Max(10)
	@Min(0)
	private Integer rarità;
	
	private String effettoCopertina;
	
	@ManyToOne
	private Editore editore;
	
	@ManyToOne
	private Manga manga;
	
	@Min(1)
	private Integer volume;
	
	/*##############################################################*/
	/*####################GETTERS AND SETTERS#######################*/
	/*##############################################################*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDataUscita() {
		return dataUscita;
	}
	public void setDataUscita(LocalDate dataUscita) {
		this.dataUscita = dataUscita;
	}
	public String getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getRarità() {
		return rarità;
	}
	public void setRarità(int rarità) {
		this.rarità = rarità;
	}
	public String getEffettoCopertina() {
		return effettoCopertina;
	}
	public void setEffettoCopertina(String effettoCopertina) {
		this.effettoCopertina = effettoCopertina;
	}
	public Editore getEditore() {
		return editore;
	}
	public void setEditore(Editore editore) {
		this.editore = editore;
	}
	public Manga getManga() {
		return manga;
	}
	public void setManga(Manga manga) {
		this.manga = manga;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getNomeVariant() {
		return nomeVariant;
	}
	public void setNomeVariant(String nomeVariant) {
		this.nomeVariant = nomeVariant;
	}
	public void setRarità(Integer rarità) {
		this.rarità = rarità;
	}
	
	/*##############################################################*/
	/*#####################EQUALS, HASHCODE#########################*/
	/*##############################################################*/
	
	@Override
	public int hashCode() {
		return Objects.hash(nomeVariant, volume);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variant other = (Variant) obj;
		return this.nomeVariant.equals(other.getNomeVariant()) && this.volume.equals(other.getVolume());
	}
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/
	
	
	
	
}
