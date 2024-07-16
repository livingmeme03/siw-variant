package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Editore;
import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	 
	@Modifying
	@Transactional(isolation = Isolation.SERIALIZABLE) //Operazione molto delicata, lascerei il lock più alto
    @Query(value = "UPDATE users SET cuoco_id = NULL", nativeQuery = true)
	void deleteCuocoAssociato(Long id);

	User findByEditore(Editore ed);

	
	
}
