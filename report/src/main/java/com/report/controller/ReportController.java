package com.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.report.dto.ReportDto;
import com.report.entity.Report;
import com.report.services.reportServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private reportServices service;
	
	    @GetMapping(value = "/listar",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	    public Mono<ResponseEntity<Flux<ReportDto>>> getAllReport(){
	        //return service.getAllReport();
	    	return Mono.just(ResponseEntity.ok()
	        	     .contentType(MediaType.APPLICATION_JSON)
	        		 .body(service.getAllReport())).defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	
	    
	    
	    @GetMapping("/{id}")
	    public Mono<ResponseEntity<ReportDto>> getReportIdT(@PathVariable String id){
	        //return service.getReportId(id);
	    	return service.getReportId(id).map(p -> ResponseEntity.ok()
	          		 .contentType(MediaType.APPLICATION_JSON)
	          		 .body(p))
	          		 .defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	
	    
	    
	    @PostMapping("/create")
	    public Mono<ResponseEntity<Report>> saveTransfer(@RequestBody ReportDto clienteDtoMono){
	        //return service.saveReport(clienteDtoMono);
	    	return service.findIdCuentaTransfer(clienteDtoMono.getIdProducto().getId()).flatMap(product->{
	    		clienteDtoMono.setIdProducto(product);
	    	
	            return service.saveReport(clienteDtoMono)
	                    .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED));
	        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	    }
	    

	    
	    
	    @PutMapping("/update/{id}")
	    public Mono<ResponseEntity<Report>> updateReport(@RequestBody ReportDto clienteDtoMono){
	        //return service.updateReport(clienteDtoMono, id);
	    	return service.findIdCuentaTransfer(clienteDtoMono.getIdProducto().getId()).flatMap(client->{
	    		clienteDtoMono.setIdProducto(client);
	            
	            return service.updateReport(clienteDtoMono)
	                    .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED));
	        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	    }
	    
	    
	    
	    
	    @DeleteMapping("/delete/{id}")
	    public  Mono<ResponseEntity<Void>>  deleteReport(@PathVariable String id){
	        //return service.deleteReport(id);
	    	 return service.getReportId(id)
	    		        .flatMap(c -> {
	    		          return service.deleteReport(c.getId())
	    		              .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	    		        })
	    		        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	    }
	    
	
	    
}
