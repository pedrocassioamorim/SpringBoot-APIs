package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Restaurante> adicionar (@RequestBody Restaurante restaurante){
        Restaurante restauranteSalvar = restauranteService.salvar(restaurante);
        return ResponseEntity.created(URI.create("URI_simulate")).body(restauranteSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Restaurante> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long Id){
        try{
            Restaurante restauranteAtualizar = restauranteService.atualizar(Id, restaurante);
            return ResponseEntity.ok().body(restauranteAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
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
