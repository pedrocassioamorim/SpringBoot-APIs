package org.pedroamorim.projetobootcamp.api.controllers.adds;

import org.pedroamorim.projetobootcamp.domain.model.adds.Estado;
import org.pedroamorim.projetobootcamp.domain.repository.adds.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository repository;

    @GetMapping
    public List<Estado> listar(){
        return repository.listar();
    }


}
