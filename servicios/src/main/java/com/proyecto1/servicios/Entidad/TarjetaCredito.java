package com.proyecto1.servicios.Entidad;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.proyecto1.servicios.Dto.ClienteDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tarjetacredito")
public class TarjetaCredito {
    @Id
    private String id;
    private double limiteCredito;
    private Date fechaapertura;
    private Date fechaPago;
    private double saldo;
    private String firmante;//dni de cliente adicional
    private ClienteDto cliente;
}
