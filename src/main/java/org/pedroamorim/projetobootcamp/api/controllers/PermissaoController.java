package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.dtos.PermissaoDto;
import org.pedroamorim.projetobootcamp.services.CadastroPermissaoService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private CadastroPermissaoService permissaoService;


    @GetMapping
    public ResponseEntity<List<PermissaoDto>> listar(){
        List<PermissaoDto> permissoes = permissaoService.listar();
        return ResponseEntity.ok().body(permissoes);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<PermissaoDto> buscar (@PathVariable Long Id){
        try{
            PermissaoDto permissao = permissaoService.buscar(Id);
            return ResponseEntity.ok().body(permissao);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PermissaoDto> salvar (@RequestBody PermissaoDto permissao){
        PermissaoDto permissaoSalvar = permissaoService.salvar(permissao);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(permissao.getId())
                .toUri();
        return ResponseEntity.created(uri).body(permissaoSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<PermissaoDto> atualizar (@PathVariable Long Id, @RequestBody PermissaoDto permissao){
        try{
            PermissaoDto permissaoAtualizar = permissaoService.atualizar(permissao, Id);
            return ResponseEntity.ok().body(permissaoAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> excluir (@PathVariable Long Id){
        try{
            permissaoService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
