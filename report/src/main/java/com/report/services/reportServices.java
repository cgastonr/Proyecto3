package com.report.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.report.dto.ProducMoviDto;
import com.report.dto.ReportDto;
import com.report.entity.Report;
import com.report.repository.ReportRepository;
import com.report.services.util.AppUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class reportServices {

	    private final WebClient webClient;
	    private final ReactiveCircuitBreaker reactiveCircuitBreaker;
	
	    String uriTransfer = "http://localhost:9494/transfer/listar/{id}";
	    
	    public reportServices(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
	        this.webClient = WebClient.builder().baseUrl(this.uriTransfer).build();
	        this.reactiveCircuitBreaker = circuitBreakerFactory.create("idProducto");
	    }
	    
	    
	    public Mono<ProducMoviDto> findIdCuentaTransfer(String id) {

	        return reactiveCircuitBreaker.run(webClient.get().uri(this.uriTransfer,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ProducMoviDto.class),
	                throwable -> {
	                    return this.getDefaultCreditos();
	                });
	    }
	    
	    //metodo
	    public Mono<ProducMoviDto> getDefaultCreditos() {
	        
	        Mono<ProducMoviDto> dtoMono = Mono.just(new ProducMoviDto("0"));
	        return dtoMono;
	    }
	    
	    
	@Autowired
	ReportRepository repository;
	
    public Flux<ReportDto> getAllReport(){
        //return repository.findAll().map(AppUtils::entityToDto);
    	 Flux<ReportDto> tranT =  repository.findAll().map(AppUtils::entityToDto);
         
         return tranT;
    }

    
    
    public Mono<ReportDto> getReportId(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }
	

    
    public Mono<Report> saveReport(ReportDto clienteDtoMono){
       /*
        return  clienteDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto); */
    	Report report = AppUtils.dtoToEntity(clienteDtoMono);
        return  repository.save(report);
    }
   
    
  
    public Mono<Report> updateReport(ReportDto clienteDtoMono){

        /* me retorna un ClienteDto y yo quiero un Cliente**/
      /*  return repository.findById(id)
                .flatMap(p->clienteDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto); */
    	Report repor= AppUtils.dtoToEntity(clienteDtoMono);
    	
    	 return repository.findById(repor.getId()).flatMap(custDB -> {
             return repository.save(repor);
         });
    	 
    }


    
    
    public Mono<Void> deleteReport(String id){
        return repository.deleteById(id);
    }
    
    

	
}

