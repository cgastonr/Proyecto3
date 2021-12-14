package com.transfer.transfer.controller;
import com.transfer.transfer.dto.TransferDto;
import com.transfer.transfer.entity.Transfer;
import com.transfer.transfer.service.TransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transfer")
public class ServicioController {
	
    @Autowired
    private TransferService service;

 
    //transferencia
    @GetMapping(value="/listar",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Flux<TransferDto>>> getAllTransfer(){
        //return service.getTransfer();
    	return Mono.just(ResponseEntity.ok()
         	     .contentType(MediaType.APPLICATION_JSON)
         		 .body(service.getTransfer())).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
   
    
    

    @GetMapping("/listar/{id}")
    public Mono<ResponseEntity<TransferDto>> getTransferPorId(@PathVariable String id){
        //return service.getTransferPorId(id);
    	return service.getTransferPorId(id).map(p -> ResponseEntity.ok()
       		 .contentType(MediaType.APPLICATION_JSON)
       		 .body(p))
       		 .defaultIfEmpty(ResponseEntity.notFound().build());
    }
 
    
   

    @PostMapping("/create")
    public Mono<ResponseEntity<Transfer>> saveTransfer(@RequestBody TransferDto cuentaDtoMono){ 
      //  return service.saveTransfer(clienteDtoMono);
        return service.findIdCuentaTransfer(cuentaDtoMono.getIdProducto().getId()).flatMap(product->{
        	cuentaDtoMono.setIdProducto(product);
            
            return service.saveTransfer(cuentaDtoMono)
                    .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    

    
    
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Transfer>>  updateTransfer(@RequestBody TransferDto cuentaDtoMono){
       // return service.updateCTransfer(clienteDtoMono, id);
    	 return service.findIdCuentaTransfer(cuentaDtoMono.getIdProducto().getId()).flatMap(client->{
    		 cuentaDtoMono.setIdProducto(client);
    		 
    		  return service.updateCTransfer(cuentaDtoMono)
                      .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED));
          }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    
    
    
  
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteTransferDto(@PathVariable String id){
        //return service.deleteTransfer(id);
    	 return service.getTransferPorId(id)
    			 .flatMap(c -> {
      		          return service.deleteTransfer(c.getId())
      		              .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
      		        })
      		        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    
  


}
