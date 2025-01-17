package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	/*#######################################################################################*/
	/*--------------------------------------REPOSITORY---------------------------------------*/
	/*#######################################################################################*/
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	/*#######################################################################################*/
	/*----------------------------------------ENCODER----------------------------------------*/
	/*#######################################################################################*/
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	
	public Credentials getCredentials(Long id) {
		return this.credentialsRepository.findById(id).get();
	}
	
	public Credentials getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).get();
	}
	
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		credentials.setRole(Credentials.EDITORE_ROLE);
		return this.credentialsRepository.save(credentials);
	}

	public boolean existsByUsername(String username) {
		return this.credentialsRepository.existsByUsername(username);
	}
	
	public Credentials findByUsername(String username) {
		return this.credentialsRepository.findByUsername(username).get();
	}

	public Credentials findByUser(User userAssociato) {
		return this.credentialsRepository.findByUser(userAssociato);
	}

	public void delete(Credentials credentialsAssociate) {
		this.credentialsRepository.delete(credentialsAssociate);
	}
	
}
