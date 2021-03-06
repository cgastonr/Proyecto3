package com.proyecto1.servicios.Service;



import com.proyecto1.servicios.Dto.ClienteDto;
import com.proyecto1.servicios.Dto.CreditoDto;
import com.proyecto1.servicios.Dto.CuentaBancariaDto;
import com.proyecto1.servicios.Dto.TarjetaCreditoDto;
import com.proyecto1.servicios.Entidad.Credito;
import com.proyecto1.servicios.Entidad.CuentaBancaria;
import com.proyecto1.servicios.Entidad.TarjetaCredito;
import com.proyecto1.servicios.Repository.CreditoRepository;
import com.proyecto1.servicios.Repository.CuentaBancariaRepository;
import com.proyecto1.servicios.Repository.TarjetaCreditoRepository;
import com.proyecto1.servicios.Utils.AppUtils;

import java.util.Observable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServiciosService {

	private static final Logger log = LoggerFactory.getLogger(ServiciosService.class);
	
    private final WebClient webClient;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    //traemos el id del cliente
    String uri = "http://localhost:9292/clientes/listar/{id}";
    public ServiciosService(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl(this.uri).build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("cliente");
    }
    
    @Autowired
    private TarjetaCreditoRepository repositoryTarjetaCredito;
    @Autowired
    private CreditoRepository repositoryCredito;
    @Autowired
    private CuentaBancariaRepository repositoryCuentaBancaria;

    // Conexion con servicio
    public Mono<ClienteDto> findTypeCustomer(String id) {
     
        return reactiveCircuitBreaker.run(webClient.get()
        		                     .uri(this.uri,id).accept(MediaType.APPLICATION_JSON)
        		                     .retrieve().bodyToMono(ClienteDto.class),
                throwable -> {
                    return this.getDefaultTypeCustomer();
                });
    }

    public Mono<ClienteDto> getDefaultTypeCustomer() {
     
        Mono<ClienteDto> cliente = Mono.just(new ClienteDto("0"));
        return cliente;
    }
    
    
    
    
    // Conexion con servicio
    public Flux<TarjetaCreditoDto> getTarjetaCreditos(){
        long start = System.currentTimeMillis();
         Flux<TarjetaCreditoDto> tarjetasCreditos =  repositoryTarjetaCredito.findAll().map(AppUtils::entityToDto);
        long end = System.currentTimeMillis();
       // System.out.println("Total execution time : " + (end - start));
        return tarjetasCreditos;
    }

    public Mono<TarjetaCreditoDto> getTarjetaCredito(String id){
        return repositoryTarjetaCredito.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<TarjetaCredito> saveTarjetaCredito(TarjetaCreditoDto creditoDtoMono){
       
        TarjetaCredito tarjetaCredito = AppUtils.dtoToEntity(creditoDtoMono);
        return  repositoryTarjetaCredito.save(tarjetaCredito);
    }

    public Mono<TarjetaCredito> updateTarjetaCredito(TarjetaCreditoDto creditoDtoMono){
        System.out.println("method updateTarjetaCredito ...");
        TarjetaCredito tarjetaCredito = AppUtils.dtoToEntity(creditoDtoMono);

        return repositoryTarjetaCredito.findById(tarjetaCredito.getId()).flatMap(custDB -> {
            return repositoryTarjetaCredito.save(tarjetaCredito);
        });

    }

    public Mono<Void> deleteTarjetaCredito(String id){
        return repositoryTarjetaCredito.deleteById(id);
    }


    
    
    
    //Creditos
    public Flux<CreditoDto> getCreditos(){
        return repositoryCredito.findAll().map(AppUtils::entityToDto);
    }

    public Mono<CreditoDto> getCredito(String id){
        return repositoryCredito.findById(id).map(AppUtils::entityToDto);

    }

    public Mono<Credito> saveCredito(CreditoDto creditoDtoMono){
    	  Credito credito = AppUtils.dtoToEntity(creditoDtoMono);
          return  repositoryCredito.save(credito);


    }

    public Mono<Credito> updateCredito(CreditoDto creditoDtoMono){

      
        Credito credito = AppUtils.dtoToEntity(creditoDtoMono);

        return repositoryCredito.findById(credito.getId()).flatMap(custDB -> {
            return repositoryCredito.save(credito);
        });

    }

    public Mono<Void> deleteCredito(String id){
        return repositoryCredito.deleteById(id);
    }

    
    
    
    
    //CuentaBancaria
    public Flux<CuentaBancariaDto> getCuentasBancarias(){
        return repositoryCuentaBancaria.findAll().map(AppUtils::entityToDto);
    }

    public Mono<CuentaBancariaDto> getCuentasBancaria(String id){
        return repositoryCuentaBancaria.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<CuentaBancaria> saveCuentasBancaria(CuentaBancariaDto cuentaBancariaDtoMono){
        //validar si el cliente solo debe teenr  1 de cada 1 (ahorro plazo ,corriente)
    	  //61a7a34af78c28147678d284 id de sofia
    	  //61a6d462f78c28147678d283 id de raul 3(ahorro plazo ,corriente)
        CuentaBancaria cuentaBancaria = AppUtils.dtoToEntity(cuentaBancariaDtoMono);
        
        //System.out.println("dato:  "+cuentaBancaria.getCliente().getId());
       // System.out.println("dato : "+cuentaBancaria.getTipo());
        
         //Flux<CuentaBancariaDto> datos =repositoryCuentaBancaria.findAll().map(AppUtils::entityToDto);
        
       // System.out.println("listado : "+repositoryCuentaBancaria.findAll() );
       
        return  repositoryCuentaBancaria.save(cuentaBancaria);
    }

    public Mono<CuentaBancaria> updateCuentasBancaria(CuentaBancariaDto cuentaBancariaDtoMono){
       
        CuentaBancaria cuentaBancaria = AppUtils.dtoToEntity(cuentaBancariaDtoMono);

        return repositoryCuentaBancaria.findById(cuentaBancaria.getId()).flatMap(custDB -> {
            return repositoryCuentaBancaria.save(cuentaBancaria);
        });
    }



    public Mono<Void> deleteCuentasBancaria(String id){
        return repositoryCuentaBancaria.deleteById(id);
    }



}
