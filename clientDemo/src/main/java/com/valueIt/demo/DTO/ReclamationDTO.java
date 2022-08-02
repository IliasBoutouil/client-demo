package com.valueIt.demo.DTO;

import com.valueIt.demo.entities.clients.Reclamation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationDTO {
    protected long id;
    protected String description;
    protected Date createdAt;

    public static  ReclamationDTO convertToDTO(Reclamation reclamation)
    {
        if(reclamation!=null)
        {
            ReclamationDTO reclamationDTO = new ReclamationDTO();
            reclamationDTO.setId(reclamation.getId());
            reclamationDTO.setDescription(reclamation.getDescription());
            reclamationDTO.setCreatedAt(reclamation.getCreatedAt());
            return  reclamationDTO;
        }
        else
            throw  new IllegalArgumentException("the reclamation must not be null !");
    }
    public static  Reclamation convertToEntity(ReclamationDTO reclamationDTO)
    {
        if(reclamationDTO!=null)
        {
            Reclamation reclamation = new Reclamation();
            reclamation.setId(reclamationDTO.getId());
            reclamation.setDescription(reclamationDTO.getDescription());
            reclamation.setCreatedAt(reclamationDTO.getCreatedAt());
            return  reclamation;
        }
        else
            throw  new IllegalArgumentException("the reclamation DTO must not be null !");
    }

}
