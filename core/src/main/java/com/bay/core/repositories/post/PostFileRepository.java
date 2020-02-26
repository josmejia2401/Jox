package com.bay.core.repositories.post;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bay.entity.core.post.TblPostFile;

@Repository
public interface PostFileRepository extends CrudRepository<TblPostFile, Long> {
	@Query(value = "SELECT u FROM TblPostFile u WHERE u.idPostCustomer =:idPostCustomer",  nativeQuery = false)
    List<TblPostFile> findByIdPost(@Param("idPostCustomer") Long idPostCustomer);
	
	//@Modifying
    //@Query("delete from TblPostFile u where u.idPostCustomer = :idPostCustomer")
	List<TblPostFile> removeByidPostCustomer(@Param("idPostCustomer") Long idPostCustomer);
}
