package com.bay.repositories.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.TblCustomer;

@Repository
public interface CustomerRepository extends CrudRepository<TblCustomer, Long> {
	
	//@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
	@Query(value = "SELECT u FROM TblCustomer u WHERE u.username =:username AND u.password =:password",  nativeQuery = false)
    Optional<TblCustomer> signIn(@Param("username") String username, @Param("password") String password);
}
