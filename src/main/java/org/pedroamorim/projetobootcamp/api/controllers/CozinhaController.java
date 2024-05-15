package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @GetMapping
    public List<Cozinha> listar(){
        return repository.listar();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long Id){
        Cozinha cozinha = repository.buscar(Id);
        if (cozinha != null){
            return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return repository.salvar(cozinha);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long Id){
        Cozinha cozinhaAtual = repository.buscar(Id);

        if (cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            repository.salvar(cozinhaAtual);
            return ResponseEntity.ok().body(cozinhaAtual);
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{Id}")
    public ResponseEntity<Cozinha> remover (@PathVariable Long Id){
        try {
            Cozinha cozinhaExluir = repository.buscar(Id);
            if (cozinhaExluir != null) {
                repository.remover(Id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



}
