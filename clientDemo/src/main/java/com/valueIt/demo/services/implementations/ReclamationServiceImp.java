package com.valueIt.demo.services.implementations;
import com.valueIt.demo.DTO.ReclamationDTO;
import com.valueIt.demo.DTO.SavingReclamationDTO;
import com.valueIt.demo.entities.clients.Reclamation;
import com.valueIt.demo.exceptions.EntityNotFoundException;
import com.valueIt.demo.exceptions.UnableToSaveEntityException;
import com.valueIt.demo.repos.clients.ReclamationRepository;
import com.valueIt.demo.services.CustomPageRequest;
import com.valueIt.demo.services.FieldsErrors;
import com.valueIt.demo.services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationServiceImp implements ReclamationService {
    final ReclamationRepository reclamationRepository;

    @Autowired
    public ReclamationServiceImp(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    @Override
    public Page<ReclamationDTO> getAllReclamations(int page,int size) {
            CustomPageRequest pageRequest = new CustomPageRequest(page,size);
            List<ReclamationDTO> reclamations = reclamationRepository.findAll(pageRequest.getPageable()).stream().map(ReclamationDTO::convertToDTO).toList();
                return new PageImpl<>(reclamations);
    }

    @Override
    public Page<ReclamationDTO> getAllReclamationsByClientID(long id,int page,int size) {
            CustomPageRequest pageRequest = new CustomPageRequest(page,size);
            List<ReclamationDTO> reclamations = reclamationRepository.findByClientId(id,pageRequest.getPageable()).stream().map(ReclamationDTO::convertToDTO).toList();
            return new PageImpl<>(reclamations);
    }

    @Override
    public ReclamationDTO getReclamationById(long id) {
        Optional<Reclamation> reclamation = reclamationRepository.findById(id);
        if(reclamation.isPresent()) {
            return ReclamationDTO.convertToDTO(reclamation.get());
        }
        else {
            throw new EntityNotFoundException("no reclamation found with id : " + id);
        }
    }

    @Override
    public ReclamationDTO saveReclamation(SavingReclamationDTO reclamation, BindingResult bindingResult) {
       try {
           Reclamation toSave = SavingReclamationDTO.convertToEntity(reclamation);
           Reclamation savedReclamation = reclamationRepository.save(toSave);
           return ReclamationDTO.convertToDTO(savedReclamation);
       }
       catch (Exception exception)
       {
           throw new UnableToSaveEntityException("error : unable to add reclamation, reasons : "+ FieldsErrors.getFieldsWithErrors(bindingResult));
       }
    }

    @Override
    public ReclamationDTO updateReclamation(SavingReclamationDTO reclamation,BindingResult bindingResult) {
           try {
            Reclamation savedReclamation = reclamationRepository.save(SavingReclamationDTO.convertToEntity(reclamation));
            return ReclamationDTO.convertToDTO(savedReclamation);
        }
        catch (Exception exception)
        {
            throw new UnableToSaveEntityException("error : unable to update reclamation, reasons : "+ FieldsErrors.getFieldsWithErrors(bindingResult));
        }
    }

    @Override
    public void deleteReclamation(long id) {
        try {
            reclamationRepository.deleteById(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException("error : unable to delete client "+id);
        }
    }
}
