package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.exceptions.RequisicaoRuimException;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService restauranteService;


    @GetMapping
    public ResponseEntity<List<Restaurante>> listar(){
        List<Restaurante> restaurantes = restauranteService.listar();
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long Id){
        try{
            Restaurante restaurante = restauranteService.buscar(Id);
            return ResponseEntity.ok().body(restaurante);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar (@RequestBody Restaurante restaurante){
        try{
            restaurante = restauranteService.salvar(restaurante);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{Id}")
                    .buildAndExpand(restaurante.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(restaurante);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long Id){
        try{
            Restaurante restauranteAtualizar = restauranteService.atualizar(Id, restaurante);
            return ResponseEntity.ok().body(restauranteAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (RequisicaoRuimException f){
            return ResponseEntity.badRequest().body(f.getMessage());
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Restaurante> deletar (@PathVariable Long Id){
        try{
            restauranteService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }




}
