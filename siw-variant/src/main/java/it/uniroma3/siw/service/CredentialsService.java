package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
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
}
