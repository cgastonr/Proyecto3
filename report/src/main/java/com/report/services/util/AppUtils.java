package com.report.services.util;
import com.report.dto.ReportDto;
import com.report.entity.Report;

import org.springframework.beans.BeanUtils;

public class AppUtils {
    //cuentas bancarias
    public static ReportDto entityToDto(Report cliente) {
    	ReportDto clienteDto = new ReportDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        return clienteDto;
    }

    public static Report dtoToEntity(ReportDto clienteDto) {
    	Report cliente = new Report();
        BeanUtils.copyProperties(clienteDto, cliente);
        return cliente;
    }
}
