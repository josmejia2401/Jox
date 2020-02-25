package com.bay.security.repositories.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.customer.TblCustomer;

@Repository
public interface CustomerRepository extends CrudRepository<TblCustomer, Long> {
	
	//@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
	@Query(value = "SELECT u FROM TblCustomer u WHERE u.username =:username AND u.password =:password",  nativeQuery = false)
    Optional<TblCustomer> signIn(@Param("username") String username, @Param("password") String password);
	
	@Query(value = "SELECT u FROM TblCustomer u WHERE u.username =:username",  nativeQuery = false)
    Optional<TblCustomer> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT u FROM TblCustomer u WHERE u.email =:email",  nativeQuery = false)
    Optional<TblCustomer> findByEmail(@Param("email") String email);
}
