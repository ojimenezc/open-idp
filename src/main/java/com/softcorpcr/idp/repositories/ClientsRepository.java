package com.softcorpcr.idp.repositories;


import com.softcorpcr.idp.model.entities.ClientsEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@ComponentScan("com.softcorpcr.idp.repositories")
@Repository
public interface ClientsRepository extends CrudRepository<ClientsEntity, Integer> {


}
