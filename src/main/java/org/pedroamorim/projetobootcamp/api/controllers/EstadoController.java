package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.domain.service.CadastroEstadoService;
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
    public ResponseEntity<List<Estado>> listar(){
        List<Estado> estados = estadoService.listar();
        return ResponseEntity.ok().body(estados);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long Id){
        try{
            Estado estado = estadoService.buscar(Id);
            return ResponseEntity.ok().body(estado);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar (@RequestBody Estado estado){
        Estado estadoSalvar = estadoService.salvar(estado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(estado.getId())
                .toUri();
        return ResponseEntity.created(uri).body(estadoSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Estado> atualizar (@RequestBody Estado estado, @PathVariable Long Id){
        try{
            Estado estadoAtualizar = estadoService.atualizar(Id, estado);
            return ResponseEntity.ok().body(estadoAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{Id}")
    public ResponseEntity<Estado> deletar (@PathVariable Long Id){
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
