package org.pedroamorim.projetobootcamp.api.controllers;


import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar(){
        return repository.listar();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long Id){
        Cozinha cozinha = repository.buscar(Id);
        // HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");

        // return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
        return ResponseEntity.status(HttpStatus.OK).body(cozinha);
    }



}
