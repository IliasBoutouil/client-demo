package com.valueIt.demo.services.implementations;

import com.valueIt.demo.DTO.AppClientDTO;
import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.exceptions.UnableToSaveEntityException;
import com.valueIt.demo.exceptions.EntityNotFoundException;
import com.valueIt.demo.repos.clients.AppClientRepository;
import com.valueIt.demo.services.AppClientService;
import com.valueIt.demo.services.CustomPageRequest;
import com.valueIt.demo.services.FieldsErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@Slf4j
public class AppClientServiceImp implements AppClientService {

    final
    AppClientRepository appClientRepository;
    static final String ALREADY_USED_MSG = " is already used, please try another one";

    @Autowired
    public AppClientServiceImp(AppClientRepository appClientRepository) {
        this.appClientRepository = appClientRepository;
    }

    @Override
    public Page<AppClientDTO> getAllClients(int page, int size) {
        log.info("Getting page {} of {} clients ", page, size);
        CustomPageRequest pageRequest = new CustomPageRequest(page, size);
        Page<AppClient> clients = appClientRepository.findByOrderByCreatedAtAsc(pageRequest.getPageable());
        log.info("Returned {} clients ", clients.getNumberOfElements());
        return new PageImpl<>(clients.stream().map(AppClientDTO::convertToDTO).toList());
    }

    @Override
    public AppClientDTO getClientById(long id) {
        log.info("Getting client with id {}", id);
        Optional<AppClient> client = appClientRepository.findById(id);
        if (client.isPresent()) {
            AppClientDTO clientDTO = AppClientDTO.convertToDTO(client.get());
            log.info("Returned {}", clientDTO);
            return clientDTO;
        } else {
            log.error("error : no client found with id : " + id);
            throw new EntityNotFoundException("no client found with id : " + id);
        }
    }

    @Override
    public AppClientDTO getClientByEmail(String email) {
        log.info("Getting client with email {}", email);
        Optional<AppClient> client = searchByEmail(email);
        if (client.isPresent()) {
            AppClientDTO clientDTO = AppClientDTO.convertToDTO(client.get());
            log.info("Returned {}", clientDTO);
            return clientDTO;
        } else {
            log.error("error : no client found with email : " + email);
            throw new EntityNotFoundException("error : no client found with email : " + email);
        }
    }

    private Optional<AppClient> searchByEmail(String email) {
        return appClientRepository.findByEmail(email);
    }


    @Override
    public AppClientDTO saveClient(AppClientDTO clientDTO, BindingResult bindingResult) {
        AppClient client=AppClientDTO.convertToEntity(clientDTO);
        if (searchByEmail(client.getEmail()).isEmpty()) {
            try {
                return AppClientDTO.convertToDTO(appClientRepository.save(client));
            } catch (Exception e) {
                throw new UnableToSaveEntityException("unable to add client, reasons : "+ FieldsErrors.getFieldsWithErrors(bindingResult));
            }
        } else
            throw new UnableToSaveEntityException("the email " + client.getEmail() + ALREADY_USED_MSG);
    }


    public AppClientDTO updateClient(AppClientDTO clientDTO, BindingResult bindingResult) {
        AppClient client=AppClientDTO.convertToEntity(clientDTO);
        if (searchByEmail(client.getEmail()).isPresent() )
            throw new UnableToSaveEntityException("error : the email " + client.getEmail() + ALREADY_USED_MSG);
        try {
            return AppClientDTO.convertToDTO(appClientRepository.save(client));
        } catch (Exception e) {
            throw new UnableToSaveEntityException("error : unable to update client " + client.getFullName() +", reasons : "+FieldsErrors.getFieldsWithErrors(bindingResult));
        }
    }


    @Override
    public void deleteClient(long id) {
        try {
            appClientRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("error : unable to delete client " + id);
        }
    }
}
