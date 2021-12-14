package com.proyecto1.clientes.Utils;


import org.springframework.beans.BeanUtils;

import com.proyecto1.clientes.Dto.ClientDto;
import com.proyecto1.clientes.Entidad.Client;



public class AppUtils {
    //cuentas bancarias
    public static ClientDto entityToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        return clientDto;
    }

    public static Client dtoToEntity(ClientDto clientDto) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return client;
    }
}
