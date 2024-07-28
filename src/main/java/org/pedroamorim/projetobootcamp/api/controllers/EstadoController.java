package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.dtos.EstadoDto;
import org.pedroamorim.projetobootcamp.services.CadastroEstadoService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService estadoService;


    @GetMapping
    public ResponseEntity<List<EstadoDto>> listar(){
        List<EstadoDto> estados = estadoService.listar();
        return ResponseEntity.ok().body(estados);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<EstadoDto> buscar(@PathVariable Long Id){
        try{
            EstadoDto estado = estadoService.buscar(Id);
            return ResponseEntity.ok().body(estado);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EstadoDto> adicionar (@RequestBody EstadoDto estado){
        EstadoDto estadoSalvar = estadoService.salvar(estado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(estado.getId())
                .toUri();
        return ResponseEntity.created(uri).body(estadoSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<EstadoDto> atualizar (@RequestBody EstadoDto estado, @PathVariable Long Id){
        try{
            EstadoDto estadoAtualizar = estadoService.atualizar(Id, estado);
            return ResponseEntity.ok().body(estadoAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deletar (@PathVariable Long Id){
        try{
            estadoService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
