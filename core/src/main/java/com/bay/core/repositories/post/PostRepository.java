package com.bay.core.repositories.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.post.TblPostCustomer;

@Repository
public interface PostRepository extends CrudRepository<TblPostCustomer, Long> {
	@Query(value = "SELECT u FROM TblPostCustomer u WHERE u.idCustomer =:idCustomer",  nativeQuery = false)
    Optional<TblPostCustomer> findByUserId(@Param("idCustomer") Long idCustomer);
}
