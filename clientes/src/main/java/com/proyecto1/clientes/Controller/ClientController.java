package com.proyecto1.clientes.Controller;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto1.clientes.Dto.ClientDto;
import com.proyecto1.clientes.Service.ClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping(value = "/listar")
    public  Mono<ResponseEntity<Flux<ClientDto>>> getClientes(){
       // return service.getClientes();
    	  return Mono.just(ResponseEntity.ok()
         	     .contentType(MediaType.APPLICATION_JSON)
         		 .body(service.getClientes()));
    }
   
    /*
     * 	@GetMapping("/listar")
	public Mono<ResponseEntity<Flux<Client>>> listar(){

         return Mono.just(ResponseEntity.ok()
        	     .contentType(MediaType.APPLICATION_JSON)
        		 .body(service.buscartodos()));
	}*/
    
    
    
    
    @GetMapping("/listar/{id}")
    public  Mono<ResponseEntity<ClientDto>> getObtenerPorId(@PathVariable String id){
        //return service.getCliente(id);
    	return service.getCliente(id).map(p -> ResponseEntity.ok()
       		 .contentType(MediaType.APPLICATION_JSON)
       		 .body(p))
       		 .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    }
    
  
    /*
      @PostMapping("/create")
    public Mono<ClientDto> saveClient(@RequestBody Mono<ClientDto> clienteDtoMono){

        return service.saveCliente(clienteDtoMono);
    }
     
     */
    

    @PostMapping("/create")
	public Mono<ResponseEntity<ClientDto>> guardar(@RequestBody Mono<ClientDto> clienteDtoMono){

         return service.saveCliente(clienteDtoMono).map(p -> ResponseEntity
        		 .created(URI.create("".concat(p.getId())))
        		 
        		 .contentType(MediaType.APPLICATION_JSON)
        		 .body(p));		
	}
/*
    @PutMapping("/update/{id}")
    public Mono<ClientDto> updateTarjetaCredito(@RequestBody Mono<ClientDto> clienteDtoMono,@PathVariable String id){
        return service.updateCliente(clienteDtoMono,id);
    }
*/    
     
    @PutMapping("/update/{id}")
	public Mono<ResponseEntity<ClientDto>> editar(@RequestBody Mono<ClientDto> clie, @PathVariable String id){
		return service.updateCliente(clie, id)
				.map(p->ResponseEntity.created(URI.create("".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	}

     
    
    
    /*
    @DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
    	return service.getCliente(id)
    			.flatMap(p ->{
			return service.deleteCliente(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    
    */
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){

	  	
    	return service.getCliente(id).flatMap(p -> {
    		
    		return service.deleteCliente(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    	}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

	}
    

	
     
}
