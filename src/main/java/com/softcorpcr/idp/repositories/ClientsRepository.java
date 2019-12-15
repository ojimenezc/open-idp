package com.softcorpcr.idp.repositories;


import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.model.entities.ClientsEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan("com.softcorpcr.idp.repositories")
@Repository
public interface ClientsRepository extends CrudRepository<ClientsEntity, Integer> {

    @Query(value = "SELECT * FROM customers c WHERE c.username=?1", nativeQuery = true)
    ClientsEntity getByUsernameOrClientId(@Param("value") String value);
}
