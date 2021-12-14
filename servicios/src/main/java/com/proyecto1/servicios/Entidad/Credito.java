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
@Document(collection = "credito")
public class Credito {
    @Id
    private String id;
    private String tipo;
    private double montoDesembolso;
    private Date fechaapertura;
    private ClienteDto cliente;

}
