package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.dtos.RestauranteDto;
import org.pedroamorim.projetobootcamp.services.CadastroRestauranteService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController @RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService restauranteService;


    @GetMapping
    public ResponseEntity<List<RestauranteDto>> listar(){
        List<RestauranteDto> restaurantes = restauranteService.listar();
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<RestauranteDto> buscar(@PathVariable Long Id){
        try{
            RestauranteDto restaurante = restauranteService.buscar(Id);
            return ResponseEntity.ok().body(restaurante);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("restaurantes/por-nome-e-frete")
    public ResponseEntity<List<RestauranteDto>> buscarPorNomeEFrete(@RequestParam String nome, @RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){
        List<RestauranteDto> restauranteDtos = restauranteService.consultarPorNomeETaxaFrete(nome, taxaInicial, taxaFinal);
        return ResponseEntity.ok().body(restauranteDtos);
    }

    @GetMapping("restaurantes/por-taxa-frete")
    public ResponseEntity<List<RestauranteDto>> listarPorTaxaFrete(@RequestParam BigDecimal taxaInitial, @RequestParam BigDecimal taxaFinal){
        List<RestauranteDto> restaurantes = restauranteService.findByTaxaFreteBetween(taxaInitial, taxaFinal);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/por-nome-e-id")
    public ResponseEntity<List<RestauranteDto>> listarPorNomeEId(@RequestParam String nome, @RequestParam Long id){
        List<RestauranteDto> restaurantes = restauranteService.findByNomeContainingAndCozinhaId(nome, id);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/primeiro-por-nome")
    public ResponseEntity<Optional<RestauranteDto>> retornaPrimeiroPorNome(@RequestParam String nome){
        Optional<RestauranteDto> restaurante = restauranteService.findFirstRestauranteByNomeContaing(nome);
        return ResponseEntity.ok().body(restaurante);
    }

    @GetMapping("restaurantes/top-2-por-nome")
    public ResponseEntity<List<RestauranteDto>> retornaTop2ByNome(@RequestParam String nome){
        List<RestauranteDto> restaurantes = restauranteService.findTop2ByNomeContaing(nome);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping("restaurantes/por-cozinha")
    public ResponseEntity<Integer> retornaNumeroDeRestaurantesPorCozinha(@RequestParam Long id){
        Integer restaurantes = restauranteService.countRestaurantesByCozinhaId(id);
        return ResponseEntity.ok().body(restaurantes);
    }

    @PostMapping
    public ResponseEntity<?> adicionar (@RequestBody RestauranteDto restaurante){
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
    public ResponseEntity<?> atualizar(@RequestBody RestauranteDto restaurante, @PathVariable Long Id){
        try{
            RestauranteDto restauranteAtualizar = restauranteService.atualizar(Id, restaurante);
            return ResponseEntity.ok().body(restauranteAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (RequisicaoRuimException f){
            return ResponseEntity.badRequest().body(f.getMessage());
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deletar (@PathVariable Long Id){
        try{
            restauranteService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{Id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long Id, @RequestBody Map<String, Object> campos){

        RestauranteDto restauranteAtualDto = restauranteService.buscar(Id);

        if (restauranteAtualDto == null){
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtualDto);

        return atualizar(restauranteAtualDto, Id);
    }

    private static void merge(Map<String, Object> campos, RestauranteDto restauranteDestinoDto) {
        campos.forEach((campo, valor) -> {
            Field field = ReflectionUtils.findField(RestauranteDto.class, campo);
            field.setAccessible(true);

            // Obter o tipo do campo no DTO:
            Class<?> fieldType = field.getType();

            if(fieldType == BigDecimal.class){
                ReflectionUtils.setField(field, restauranteDestinoDto, new BigDecimal(String.valueOf(valor)));
            } else if (fieldType.isAssignableFrom(valor.getClass())) {
                ReflectionUtils.setField(field, restauranteDestinoDto, valor);
            } else {
                throw new IllegalArgumentException("Valor incorreto para o campo:" + campo.toString());
            }

            System.out.println(campo + " = " + valor);
        });
    }


}
