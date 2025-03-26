package com.example.demo.teste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends MongoRepository<Teste, String> {

}
