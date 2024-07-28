package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.dtos.CidadeDto;
import org.pedroamorim.projetobootcamp.services.CadastroCidadeService;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cidadeService;


    @GetMapping
    public ResponseEntity<List<CidadeDto>> listar (){
        List<CidadeDto> cidades = cidadeService.listar();
        return ResponseEntity.ok().body(cidades);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<CidadeDto> buscar(@PathVariable Long Id){
        try{
            CidadeDto cidade = cidadeService.buscar(Id);
            return ResponseEntity.ok().body(cidade);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar (@RequestBody CidadeDto cidadeDto){
        try{
            CidadeDto cidadeSalvar = cidadeService.salvar(cidadeDto);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{Id}")
                    .buildAndExpand(cidadeDto.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(cidadeSalvar);
        } catch (RequisicaoRuimException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> atualizar (@RequestBody CidadeDto cidadeDto, @PathVariable Long Id){
        try{
            CidadeDto cidadeAtualizar = cidadeService.atualizar(Id, cidadeDto);
            return ResponseEntity.ok().body(cidadeAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (RequisicaoRuimException f){
            return ResponseEntity.badRequest().body(f.getMessage());
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<CidadeDto> excluir (@PathVariable Long Id){
        try{
            cidadeService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
