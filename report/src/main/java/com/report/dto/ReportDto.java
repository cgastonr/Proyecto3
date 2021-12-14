package com.report.dto;

import org.springframework.data.annotation.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {


	private String id;
	private Double monto_diario;
	private Double cuentaC_comision;
	private ProducMoviDto idProducto;
	
	
}
