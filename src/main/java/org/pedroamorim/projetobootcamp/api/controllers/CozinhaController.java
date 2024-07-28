package org.pedroamorim.projetobootcamp.api.controllers;


import org.pedroamorim.projetobootcamp.domain.dtos.CozinhaDto;
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


@RestController @RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @GetMapping
    public ResponseEntity<List<CozinhaDto>> listar(){
        List<CozinhaDto> cozinhas = cozinhaService.listar();
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("cozinhas/unico-por-nome")
    public ResponseEntity<CozinhaDto> findUnicoByName(@RequestParam String nome){
        CozinhaDto cozinha = cozinhaService.findUnicoByNome(nome);
        return ResponseEntity.ok().body(cozinha);
    }


    @GetMapping("cozinhas/existe")
    public ResponseEntity<Boolean> existeCozinha(@RequestParam String nome){
        Boolean exists = cozinhaService.existsByName(nome);
        return ResponseEntity.ok().body(exists);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<CozinhaDto> buscar(@PathVariable Long Id){
        try{
            CozinhaDto cozinha = cozinhaService.findById(Id);
            return ResponseEntity.ok().body(cozinha);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cozinhas/por-nome")
    public ResponseEntity<List<CozinhaDto>> buscarPorNome(@RequestParam String nome){
        List<CozinhaDto> cozinhas = cozinhaService.listarPorNome(nome);
        return ResponseEntity.ok().body(cozinhas);
    }

    @PostMapping
    public ResponseEntity<CozinhaDto> adicionar(@RequestBody CozinhaDto cozinha){
        CozinhaDto cozinhaSalvar = cozinhaService.salvar(cozinha);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(cozinha.getId())
                .toUri();
        return ResponseEntity.created(uri).body(cozinhaSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> atualizar(@PathVariable("Id") Long Id, @RequestBody CozinhaDto cozinha){
        try{
            CozinhaDto cozinhaAtualizar = cozinhaService.atualizar(Id, cozinha);
            return ResponseEntity.ok().body(cozinhaAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deletar(@PathVariable Long Id){
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
