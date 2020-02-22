package com.bay.repositories.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.location.TblLocation;

@Repository
public interface LocationRepository extends CrudRepository<TblLocation, Long> {
	
}
