package com.bay.repositories.customer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.token.TblToken;

@Repository
public interface TokenRepository extends CrudRepository<TblToken, Long> {
	
	@Query(value = "SELECT u FROM TblToken u WHERE u.idCustomer =:idCustomer AND u.token =:token AND u.status = 'C' AND u.expiryDate >= :expiryDate",  nativeQuery = false)
    Optional<TblToken> findTokenUserId(@Param("idCustomer") Long idCustomer, @Param("token") String token, @Param("expiryDate") LocalDateTime expiryDate);
	
	@Query(value = "SELECT u FROM TblToken u WHERE u.idCustomer =:idCustomer AND u.status = 'C' AND u.expiryDate >= :expiryDate",  nativeQuery = false)
    Optional<TblToken> findTokenValid(@Param("idCustomer") Long idCustomer, @Param("expiryDate") LocalDateTime expiryDate);
}
