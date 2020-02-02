package com.bay.repositories.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.TblToken;

@Repository
public interface TokenRepository extends CrudRepository<TblToken, Long> {
	
}
