package com.transfer.transfer.dto;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

	    private String id;
	    private String tipo;
	    private String id_cta_origen;
	    private String id_cta_destino;
	    private double monto;
	    private Date fecha;
	    private CuentaBnacariaDto idProducto;
	 
	
}
