package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.services.CadastroRestauranteService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("restaurantes/por-taxa-frete")
    public ResponseEntity<List<Restaurante>> listarPorTaxaFrete(@RequestParam BigDecimal taxaInitial, @RequestParam BigDecimal taxaFinal){
        List<Restaurante> restaurantes = restauranteService.findByTaxaFreteBetween(taxaInitial, taxaFinal);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/por-nome-e-id")
    public ResponseEntity<List<Restaurante>> listarPorNomeEId(@RequestParam String nome, @RequestParam Long id){
        List<Restaurante> restaurantes = restauranteService.findByNomeContainingAndCozinhaId(nome, id);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/primeiro-por-nome")
    public ResponseEntity<Optional<Restaurante>> retornaPrimeiroPorNome(@RequestParam String nome){
        Optional<Restaurante> restaurante = restauranteService.findFirstRestauranteByNomeContaing(nome);
        return ResponseEntity.ok().body(restaurante);
    }

    @GetMapping("restaurantes/top-2-por-nome")
    public ResponseEntity<List<Restaurante>> retornaTop2ByNome(@RequestParam String nome){
        List<Restaurante> restaurantes = restauranteService.findTop2ByNomeContaing(nome);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/por-cozinha")
    public ResponseEntity<Integer> retornaNumeroDeRestaurantesPorCozinha(@RequestParam Long id){
        Integer restaurantes = restauranteService.countRestaurantesByCozinhaId(id);
        return ResponseEntity.ok().body(restaurantes);
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
