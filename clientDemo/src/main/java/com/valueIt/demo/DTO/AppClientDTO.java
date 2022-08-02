package com.valueIt.demo.DTO;

import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.entities.clients.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class AppClientDTO {
    private long id;
    private String fullName;
    private String email;
    private Date birthDay;
    private ClientType clientType;
    private Date createdAt;

    public AppClientDTO(long id, String email) {
        this.id = id;
        this.email = email;
    }
    public AppClientDTO(long id,String fullName,String email) {
        this.id = id;
        this.fullName=fullName;
        this.email = email;
    }

    public static AppClientDTO convertToDTO(AppClient client)
    {
        if(client!=null) {
            AppClientDTO clientDTO = new AppClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setFullName(client.getFullName());
            clientDTO.setEmail(client.getEmail());
            clientDTO.setBirthDay(client.getBirthDay());
            clientDTO.setClientType(client.getClientType());
            clientDTO.setCreatedAt(client.getCreatedAt());
            return clientDTO;
        }
        else
        {
            throw new IllegalArgumentException("the client must not be null !");
        }
    }

    public static AppClient convertToEntity(AppClientDTO clientDTO)
    {
       if(clientDTO!=null)
       {
           AppClient client = new AppClient();
           client.setFullName(clientDTO.getFullName());
           client.setEmail(clientDTO.getEmail());
           client.setId(clientDTO.getId());
           client.setBirthDay(clientDTO.getBirthDay());
           client.setClientType(clientDTO.getClientType());
           client.setCreatedAt(clientDTO.getCreatedAt());
           return  client;
       }
       else
       {
           throw new IllegalArgumentException("the client DTO must not be null !");
       }
    }
}
