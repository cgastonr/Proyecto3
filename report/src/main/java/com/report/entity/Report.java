package com.report.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.report.dto.ProducMoviDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "report")
public class Report {
    
	@Id
	private String id;
	private Double monto_diario;
	private Double cuentaC_comision;
	private ProducMoviDto idProducto;
	
	
}
