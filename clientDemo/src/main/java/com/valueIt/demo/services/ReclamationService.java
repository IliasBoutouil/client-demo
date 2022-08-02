package com.valueIt.demo.services;


import com.valueIt.demo.DTO.ReclamationDTO;
import com.valueIt.demo.DTO.SavingReclamationDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

public interface ReclamationService {
    Page<ReclamationDTO> getAllReclamations(int page,int size);
    Page<ReclamationDTO> getAllReclamationsByClientID(long id,int page,int size);
    ReclamationDTO getReclamationById(long id);
    ReclamationDTO saveReclamation(SavingReclamationDTO reclamation, BindingResult bindingResult);
    ReclamationDTO updateReclamation(SavingReclamationDTO reclamation, BindingResult bindingResult);
    void deleteReclamation(long id);
}
