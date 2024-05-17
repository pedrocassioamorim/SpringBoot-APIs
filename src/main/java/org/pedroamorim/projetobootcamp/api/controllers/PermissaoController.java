package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Permissao;
import org.pedroamorim.projetobootcamp.domain.service.CadastroPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private CadastroPermissaoService permissaoService;


    @GetMapping
    public ResponseEntity<List<Permissao>> listar(){
        List<Permissao> permissoes = permissaoService.listar();
        return ResponseEntity.ok().body(permissoes);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Permissao> buscar (@PathVariable Long Id){
        try{
            Permissao permissao = permissaoService.buscar(Id);
            return ResponseEntity.ok().body(permissao);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Permissao> salvar (@RequestBody Permissao permissao){
        Permissao permissaoSalvar = permissaoService.salvar(permissao);
        return ResponseEntity.created(URI.create("URI_simulated")).body(permissaoSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Permissao> atualizar (@PathVariable Long Id, @RequestBody Permissao permissao){
        try{
            Permissao permissaoAtualizar = permissaoService.atualizar(permissao, Id);
            return ResponseEntity.ok().body(permissaoAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Permissao> excluir (@PathVariable Long Id){
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
