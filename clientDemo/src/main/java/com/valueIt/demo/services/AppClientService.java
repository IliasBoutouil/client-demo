package com.valueIt.demo.services;

import com.valueIt.demo.DTO.AppClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

public interface AppClientService {
     Page<AppClientDTO> getAllClients(int pageNumber, int size);
     AppClientDTO getClientById(long id);
     AppClientDTO getClientByEmail(String email);
     AppClientDTO updateClient(AppClientDTO client,BindingResult bindingResult);
     AppClientDTO saveClient(AppClientDTO client, BindingResult bindingResult);
     void deleteClient(long id);
}
