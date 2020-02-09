package com.softcorpcr.idp.repositories;


import com.softcorpcr.idp.model.entities.TokensEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan("com.softcorpcr.idp.repositories")
@Repository
public interface TokensRepository extends CrudRepository<TokensEntity, Integer> {
    @Query(value = "SELECT *  FROM tokens WHERE client_credentials=?1 AND grant_type=?2 AND expiry_date>NOW() LIMIT 1", nativeQuery = true)
    TokensEntity getCurrentToken(@Param("client_credentials") String clientCredentials, @Param("grant_type") String grantType);
}
