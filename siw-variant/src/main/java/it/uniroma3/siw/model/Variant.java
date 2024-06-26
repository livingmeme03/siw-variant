package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

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
	
	@NotNull
	//Potremmo volere variant che usciranno in futuro, non usare @Past
	private LocalDate dataUscita;
	
	
	private String pathImmagine;
	
	
	private String descrizione;
	
	@NotNull
	@Max(10)
	@Min(0)
	private int rarità;
	
	@NotBlank
	private String effettoCopertina;
	
	@NotNull
	@ManyToOne 
	private Editore editore;
	
	@NotNull
	@ManyToOne
	private Manga manga;
	
	//Il volume non deve superare il numero di volumi del manga
	@NotNull
	@Min(1)
	private int volume;
	
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
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	/*##############################################################*/
	/*#####################EQUALS, HASHCODE#########################*/
	/*##############################################################*/
	
	@Override
	public int hashCode() {
		return Objects.hash(dataUscita, editore, effettoCopertina, manga, rarità, volume);
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
		return Objects.equals(dataUscita, other.dataUscita) && Objects.equals(editore, other.editore)
				&& Objects.equals(effettoCopertina, other.effettoCopertina) && Objects.equals(manga, other.manga)
				&& rarità == other.rarità && volume == other.volume;
	}
	
	/*##############################################################*/
	/*#######################CLASS METHODS##########################*/
	/*##############################################################*/
	
	
	
}
