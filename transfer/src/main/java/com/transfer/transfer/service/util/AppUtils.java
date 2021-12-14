package com.transfer.transfer.service.util;


import com.transfer.transfer.dto.TransferDto;
import com.transfer.transfer.entity.Transfer;

import org.springframework.beans.BeanUtils;

public class AppUtils {
    
    
    //cuentas bancarias
    public static TransferDto  entityToDto(Transfer cuentaBancaria) {
    	TransferDto  cuentaBancariaDto = new TransferDto ();
        BeanUtils.copyProperties(cuentaBancaria, cuentaBancariaDto);
        return cuentaBancariaDto;
    }

    public static Transfer dtoToEntity(TransferDto  cuentaBancariaDto) {
    	Transfer cuentaBancaria = new Transfer();
        BeanUtils.copyProperties(cuentaBancariaDto, cuentaBancaria);
        return cuentaBancaria;
    }
}
