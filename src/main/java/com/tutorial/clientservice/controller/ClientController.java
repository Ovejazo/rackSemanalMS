package com.tutorial.clientservice.controller;

import com.tutorial.clientservice.entity.ClientEntity;
import com.tutorial.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<ClientEntity>> listClient() {
        List<ClientEntity> clients = clientService.getClient();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientbyId(@PathVariable Long id) {
        ClientEntity clients = clientService.getClientById(id);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClientEntity> getClientbyRut(@PathVariable String rut) {
        ClientEntity clients = clientService.getClientByRut(rut);
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/")
    public ResponseEntity<ClientEntity> saveClient(@RequestBody ClientEntity client) {
        ClientEntity clientsNew = clientService.saveClient(client);
        return ResponseEntity.ok(clientsNew);
    }

    @PutMapping("/")
    public ResponseEntity<ClientEntity> updateClient(@RequestBody ClientEntity client){
        ClientEntity clientUpdate = clientService.updateClient(client);
        return ResponseEntity.ok(clientUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClientById(@PathVariable Long id) throws Exception {
        var isDeleted = clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
