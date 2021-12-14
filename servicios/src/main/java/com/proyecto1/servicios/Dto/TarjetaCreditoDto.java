package com.proyecto1.servicios.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCreditoDto {
    private String id;
    private double limiteCredito;
    private Date fechaapertura;
    private Date fechaPago;
    private double saldo;
    private String firmante;//dni de cliente adicional
    private ClienteDto cliente;
}
