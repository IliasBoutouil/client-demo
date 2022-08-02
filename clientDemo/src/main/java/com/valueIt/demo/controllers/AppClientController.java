package com.valueIt.demo.controllers;

import com.valueIt.demo.DTO.AppClientDTO;
import com.valueIt.demo.services.AppClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/api/clients")
public class AppClientController
{
    AppClientService clientService;

    public AppClientController(AppClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
    ResponseEntity<List<AppClientDTO>> index(@RequestParam (defaultValue = "1") int page, @RequestParam (defaultValue = "10") int size)
    {
            return ResponseEntity.ok(clientService.getAllClients(page-1,size).getContent());
    }
    @GetMapping("/{id}")
    ResponseEntity<AppClientDTO> show(@PathVariable long id)
    {
        return  ResponseEntity.ok(clientService.getClientById(id));
    }
    @PostMapping
    ResponseEntity<AppClientDTO> store(@RequestBody @Valid AppClientDTO client, BindingResult bindingResult)
    {
            return ResponseEntity.ok(clientService.saveClient(client,bindingResult));
    }
    //Todo : replace client with DTO
    @PutMapping
    ResponseEntity<AppClientDTO> update(@RequestBody @Valid AppClientDTO client,BindingResult bindingResult)
    {
            return ResponseEntity.ok(clientService.updateClient(client,bindingResult));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable long id)
    {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok("deleted successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
