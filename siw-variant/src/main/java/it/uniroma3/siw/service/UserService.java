package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {
	
	/*#######################################################################################*/
	/*--------------------------------------REPOSITORY---------------------------------------*/
	/*#######################################################################################*/
	
	@Autowired
	public UserRepository userRepository;
	
	/*#######################################################################################*/
	/*----------------------------------------METHODS----------------------------------------*/
	/*#######################################################################################*/
	
	public User getUser(Long id) {
		return this.userRepository.findById(id).get();
	}
	
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	public User findByEditore(Editore ed) {
		try {
			return this.userRepository.findByEditore(ed);
		}
		catch (Exception e) {
			return null;
		}
	}
}
