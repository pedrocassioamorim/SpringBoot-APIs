package org.pedroamorim.projetobootcamp.api.controllers;


import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.services.CadastroCozinhaService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController @RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar(){
        List<Cozinha> cozinhas = cozinhaService.listar();
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("cozinhas/unico-por-nome")
    public ResponseEntity<Optional<Cozinha>> findUnicoByName(@RequestParam String nome){
        Optional<Cozinha> cozinha = cozinhaService.findUnicoByNome(nome);
        return ResponseEntity.ok().body(cozinha);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long Id){
        try{
            Cozinha cozinha = cozinhaService.buscar(Id);
            return ResponseEntity.ok().body(cozinha);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> buscarPorNome(@RequestParam String nome){
        List<Cozinha> cozinhas = cozinhaService.listarPorNome(nome);
        return ResponseEntity.ok().body(cozinhas);
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha){
        Cozinha cozinhaSalvar = cozinhaService.salvar(cozinha);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(cozinha.getId())
                .toUri();
        return ResponseEntity.created(uri).body(cozinhaSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> atualizar(@PathVariable("Id") Long Id, @RequestBody Cozinha cozinha){
        try{
            Cozinha cozinhaAtualizar = cozinhaService.atualizar(Id, cozinha);
            return ResponseEntity.ok().body(cozinhaAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long Id){
        try{
            cozinhaService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



}
