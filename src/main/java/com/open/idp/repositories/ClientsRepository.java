package com.open.idp.repositories;


import com.open.idp.model.entities.ClientsEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan("com.open.idp.repositories")
@Repository
public interface ClientsRepository extends CrudRepository<ClientsEntity, Integer> {

    @Query(value = "SELECT * FROM customers c WHERE c.username=?1", nativeQuery = true)
    ClientsEntity getByUsernameOrClientId(@Param("value") String value);
}
