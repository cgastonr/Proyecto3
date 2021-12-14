package com.transfer.transfer.service;


import com.transfer.transfer.dto.CuentaBnacariaDto;
import com.transfer.transfer.dto.TransferDto;
import com.transfer.transfer.entity.Transfer;
import com.transfer.transfer.repository.TransferRepository;
import com.transfer.transfer.service.util.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class TransferService {
	
	    private final WebClient webClient;
	    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

	    String uricuentaBancaria = "http://localhost:9393/servicios/cuentasBancarias/{id}";
	    
	    public TransferService(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
	        this.webClient = WebClient.builder().baseUrl(this.uricuentaBancaria).build();
	        this.reactiveCircuitBreaker = circuitBreakerFactory.create("idProducto");
	    }
	    
	    // Conexion con servicio
	    
	    
	    public Mono<CuentaBnacariaDto> findIdCuentaTransfer(String id) {

	        return reactiveCircuitBreaker.run(webClient.get().uri(this.uricuentaBancaria,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(CuentaBnacariaDto.class),
	                throwable -> {
	                    return this.getDefaultCreditos();
	                });
	    }
	    
	    
	    
	    
	    //metodo
	    public Mono<CuentaBnacariaDto> getDefaultCreditos() {
	        
	        Mono<CuentaBnacariaDto> dtoMono = Mono.just(new CuentaBnacariaDto("0"));
	        return dtoMono;
	    }

	    
	

    @Autowired
    private TransferRepository repository;

    //Creditos
    public Flux<TransferDto> getTransfer(){
        return repository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<TransferDto> getTransferPorId(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }
    
    

    public Mono<Transfer> saveTransfer(TransferDto transfDtoMono){
      
       /* return transfDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto); */
    	
    	Transfer trans=AppUtils.dtoToEntity(transfDtoMono);
    	return  repository.save(trans);
    }


    public Mono<Transfer> updateCTransfer(TransferDto trasnfDtoMono){
     
        /*
        return repository.findById(id)
                .flatMap(p->clienteDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
        */
    	Transfer transf=AppUtils.dtoToEntity(trasnfDtoMono);
    	 return repository.findById(transf.getId()).flatMap(custDB -> {
             return repository.save(transf);
         });
    }
    
    /*
    public Mono<Movimientos> updateMovimientoCuentaBancaria(MovimientosDto movimientosDtoMono){

        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);

        return repository.findById(movimientos.getId()).flatMap(custDB -> {
            return repository.save(movimientos);
        });
    }
    */
    
    
    
    
    
    

    public Mono<Void> deleteTransfer(String id){
        return repository.deleteById(id);
    }


    
    
}
