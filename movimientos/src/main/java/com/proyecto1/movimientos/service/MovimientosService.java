package com.proyecto1.movimientos.service;

import com.proyecto1.movimientos.Repository.MovimientosRepository;
import com.proyecto1.movimientos.dto.MovimientosDto;
import com.proyecto1.movimientos.dto.ProductoDto;
import com.proyecto1.movimientos.entity.Movimientos;
import com.proyecto1.movimientos.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientosService {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    String uriCreditos = "http://localhost:9393/servicios/creditos/{id}";
    String uriTarjetasCredito = "http://localhost:9393/servicios/tarjetasCredito/{id}";
    String uriCuentaBancaria = "http://localhost:9393/servicios/cuentasBancarias/{id}";

    
    public MovimientosService(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl(this.uriCreditos).build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("idProducto");
    }
    
    
    @Autowired
    private MovimientosRepository repository;


    // Conexion con servicio
    public Mono<ProductoDto> findIdCreditos(String id) {
       
        return reactiveCircuitBreaker.run(webClient.get().uri(this.uriCreditos,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ProductoDto.class),
                throwable -> {
                    return this.getDefaultCreditos();
                });
    }
   
    public Mono<ProductoDto> findIdTarjetasCreditos(String id) {
       
        return reactiveCircuitBreaker.run(webClient.get().uri(this.uriTarjetasCredito,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ProductoDto.class),
                throwable -> {
                    return this.getDefaultCreditos();
                });
    }
    
    public Mono<ProductoDto> findIdCuentaBancaria(String id) {

        return reactiveCircuitBreaker.run(webClient.get().uri(this.uriCuentaBancaria,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ProductoDto.class),
                throwable -> {
                    return this.getDefaultCreditos();
                });
    }
    
    
  
    //metodo
    public Mono<ProductoDto> getDefaultCreditos() {
        
        Mono<ProductoDto> dtoMono = Mono.just(new ProductoDto("0"));
        return dtoMono;
    }

    
  
    public Mono<ProductoDto> getDefaultTarjetasCreditos() {
     
        Mono<ProductoDto> dtoMono = Mono.just(new ProductoDto("0"));
        return dtoMono;
    }
    
    public Mono<ProductoDto> getDefaultCuentaBancaria() {
       
        Mono<ProductoDto> dtoMono = Mono.just(new ProductoDto("0"));
        return dtoMono;
    }
    
    
    
    
    
    

    // Conexion con servicio credito
    public Flux<MovimientosDto> getMovimientoscreditos(){     
        Flux<MovimientosDto> movimientos =  repository.findAll().map(AppUtils::entityToDto);
      
        return movimientos;
    }

    public Mono<MovimientosDto> getMovimientocredito(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<Movimientos> saveMovimientoCredito(MovimientosDto movimientosDtoMono){
       
        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);
        return  repository.save(movimientos);
    }

    public Mono<Movimientos> updateMovimientoCredito(MovimientosDto movimientosDtoMono){

        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);

        return repository.findById(movimientos.getId()).flatMap(custDB -> {
            return repository.save(movimientos);
        });
    }

    public Mono<Void> deleteMovimientoCredito(String id){
      
        return repository.deleteById(id);
    }

    /*fin crud credito*/
    
    
    
    /*  inicio tarjeta de credito*/
    
    public Flux<MovimientosDto> getMovimientosTarjetascreditos(){     
        Flux<MovimientosDto> movimientos =  repository.findAll().map(AppUtils::entityToDto);
      
        return movimientos;
    }

    public Mono<MovimientosDto> getMovimientoTarjetacredito(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<Movimientos> saveMovimientoTarjetascreditos(MovimientosDto movimientosDtoMono){
       
        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);
        return  repository.save(movimientos);
    }

    public Mono<Movimientos> updateMovimientoTarjetascreditos(MovimientosDto movimientosDtoMono){

        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);

        return repository.findById(movimientos.getId()).flatMap(custDB -> {
            return repository.save(movimientos);
        });
    }

    public Mono<Void> deleteMovimientoTarjetascreditos(String id){
      
        return repository.deleteById(id);
    }
    
    /*fin tarjeta de creadito*/
    
    
  /*  inicio cuenta bancaria*/
    
    public Flux<MovimientosDto> getMovimientosCuentasBancarias(){     
        Flux<MovimientosDto> movimientos =  repository.findAll().map(AppUtils::entityToDto);
      
        return movimientos;
    }

    public Mono<MovimientosDto> getMovimientoCuentaBancaria(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<Movimientos> saveMovimientoCuentaBancaria(MovimientosDto movimientosDtoMono){
       
        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);
        return  repository.save(movimientos);
    }

    
    
    public Mono<Movimientos> updateMovimientoCuentaBancaria(MovimientosDto movimientosDtoMono){

        Movimientos movimientos = AppUtils.dtoToEntity(movimientosDtoMono);

        return repository.findById(movimientos.getId()).flatMap(custDB -> {
            return repository.save(movimientos);
        });
    }

    
    
    public Mono<Void> deleteMovimientoCuentaBancaria(String id){
      
        return repository.deleteById(id);
    }
    
    /* fin cuenta bancaria*/
    
    
    
 
    
    
}
