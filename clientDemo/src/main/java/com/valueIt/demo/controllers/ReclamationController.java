package com.valueIt.demo.controllers;
import com.valueIt.demo.DTO.ReclamationDTO;
import com.valueIt.demo.DTO.SavingReclamationDTO;
import com.valueIt.demo.services.ReclamationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/api/reclamations")
public class ReclamationController {

    ReclamationService reclamationService;

    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @GetMapping
    ResponseEntity<List<ReclamationDTO>> index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reclamationService.getAllReclamations(page - 1, size).getContent());
    }
    @GetMapping("/client/{id}")
    ResponseEntity<List<ReclamationDTO>> getClientsReclamations(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,@PathVariable long id) {
        return ResponseEntity.ok(reclamationService.getAllReclamationsByClientID(id,page - 1, size).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<ReclamationDTO> show(@PathVariable long id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    @PostMapping
    ResponseEntity<ReclamationDTO> store(@RequestBody @Valid SavingReclamationDTO reclamation, BindingResult bindingResult) {
        return ResponseEntity.ok(reclamationService.saveReclamation(reclamation, bindingResult));
    }

    @PutMapping
    ResponseEntity<ReclamationDTO> update(@RequestBody @Valid SavingReclamationDTO reclamation, BindingResult bindingResult) {
        return ResponseEntity.ok(reclamationService.updateReclamation(reclamation, bindingResult));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable long id)
    {
        try {
            reclamationService.deleteReclamation(id);
            return ResponseEntity.ok("deleted successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
