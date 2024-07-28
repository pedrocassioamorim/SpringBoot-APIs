package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.dtos.FormaPagamentoDto;
import org.pedroamorim.projetobootcamp.services.CadastroFormaPagamentoService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/formasDePagamentos")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoDto>> listar(){
        List<FormaPagamentoDto> formaPagamentos = formaPagamentoService.listar();
        return ResponseEntity.ok().body(formaPagamentos);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable Long Id){
        try{
            FormaPagamentoDto formaPagamento = formaPagamentoService.buscar(Id);
            return ResponseEntity.ok().body(formaPagamento);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDto> salvar (@RequestBody FormaPagamentoDto formaPagamento){
        FormaPagamentoDto formaPagamentoSalvar = formaPagamentoService.salvar(formaPagamento);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(formaPagamento.getId())
                .toUri();
        return ResponseEntity.created(uri).body(formaPagamentoSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<FormaPagamentoDto> atualizar (@RequestBody FormaPagamentoDto formaPagamento, @PathVariable Long Id){
        try{
            FormaPagamentoDto formaPagamentoAtualizar = formaPagamentoService.atualizar(Id, formaPagamento);
            return ResponseEntity.ok().body(formaPagamentoAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> excluir (@PathVariable Long Id){
        try{
            formaPagamentoService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
