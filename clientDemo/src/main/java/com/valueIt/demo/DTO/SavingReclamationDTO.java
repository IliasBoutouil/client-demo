package com.valueIt.demo.DTO;

import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.entities.clients.Reclamation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SavingReclamationDTO extends ReclamationDTO{
    private AppClient client;
     public static Reclamation convertToEntity(SavingReclamationDTO savingReclamationDTO)
     {
         Reclamation reclamation = ReclamationDTO.convertToEntity(savingReclamationDTO);
         reclamation.setClient(savingReclamationDTO.getClient());
         return reclamation;
     }

}
