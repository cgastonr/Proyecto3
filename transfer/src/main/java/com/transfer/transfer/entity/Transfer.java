package com.transfer.transfer.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.transfer.transfer.dto.CuentaBnacariaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tranfer")
public class Transfer {

	    @Id
	    private String id;
	    private String tipo;
	    private String id_cta_origen;
	    private String id_cta_destino;
	    private double monto;
	    private Date fecha;
	    private CuentaBnacariaDto idProducto;
	   

}
