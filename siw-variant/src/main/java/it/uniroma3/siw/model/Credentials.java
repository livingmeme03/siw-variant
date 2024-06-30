package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credentials {
	
	/*#######################################################################################*/
	/*-----------------------------------STATIC ROLES----------------------------------------*/
	/*#######################################################################################*/
	
	public static final String EDITORE_ROLE = "EDITORE";
	public static final String ADMIN_ROLE = "ADMIN";
	
	/*#######################################################################################*/
	/*-------------------------------------VARIABLES-----------------------------------------*/
	/*#######################################################################################*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private String role;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	/*#######################################################################################*/
	/*--------------------------------GETTERS AND SETTERS------------------------------------*/
	/*#######################################################################################*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public static String getEditoreRole() {
		return EDITORE_ROLE;
	}
	public static String getAdminRole() {
		return ADMIN_ROLE;
	}
	
	/*#######################################################################################*/
	/*------------------------------------CLASS METHODS--------------------------------------*/
	/*#######################################################################################*/
	
}
