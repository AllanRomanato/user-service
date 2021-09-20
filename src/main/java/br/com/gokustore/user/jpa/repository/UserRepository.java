package br.com.gokustore.user.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gokustore.user.jpa.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(nativeQuery = true,
			value = "SELECT * FROM user WHERE email = :email and password = :password")
	User getUserInfoByCredentials(@Param("email") String email, @Param("password") String password);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM user WHERE email = :email")
	User checkUserEmail(@Param("email") String email);
}
