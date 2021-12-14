package com.proyecto1.clientes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto1.clientes.Dto.ClientDto;
import com.proyecto1.clientes.Repository.ClientRepository;
import com.proyecto1.clientes.Utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    //Creditos
    public Flux<ClientDto> getClientes(){
        return repository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ClientDto> getCliente(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<ClientDto> saveCliente(Mono<ClientDto> clienteDtoMono){
        
        return  clienteDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ClientDto> updateCliente(Mono<ClientDto> clienteDtoMono,String id){
        System.out.println("service method called ...");

        return repository.findById(id)
                .flatMap(p->clienteDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);

    }

    public Mono<Void> deleteCliente(String id){
        return repository.deleteById(id);
    }
}
