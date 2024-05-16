package org.pedroamorim.projetobootcamp.api.controllers;


import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar(){
        List<Cozinha> cozinhas = cozinhaService.listar();
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long Id){
        try{
            Cozinha cozinha = cozinhaService.buscar(Id);
            return ResponseEntity.ok().body(cozinha);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha){
        Cozinha cozinhaSalvar = cozinhaService.salvar(cozinha);
        return ResponseEntity.created(URI.create("URI_simulate")).body(cozinhaSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long Id, @RequestBody Cozinha cozinha){
        try{
            Cozinha cozinhaAtualizar = cozinhaService.atualizar(Id, cozinha);
            return ResponseEntity.ok().body(cozinhaAtualizar);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long Id){
        try{
            cozinhaService.excluir(Id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }




}
