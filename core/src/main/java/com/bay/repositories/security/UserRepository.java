package com.bay.repositories.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	//@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
	@Query(value = "SELECT u FROM UserEntity u WHERE u.username =:username AND u.password =:password",  nativeQuery = false)
    Optional<UserEntity> signIn(@Param("username") String username, @Param("password") String password);
}
