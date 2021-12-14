package com.proyecto1.clientes.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.proyecto1.clientes.Entidad.Client;



@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client,String> {
}
