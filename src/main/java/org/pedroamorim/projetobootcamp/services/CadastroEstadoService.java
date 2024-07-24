package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.repositories.EstadoRepository;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }


    public Estado buscar(Long Id){
        Optional<Estado> estado = estadoRepository.findById(Id);
        if(estado.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        }
        return estado.get();
    }

    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Long Id, Estado estado){
        Optional<Estado> estadoAtualizar = estadoRepository.findById(Id);
        if (estadoAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        } else {
            BeanUtils.copyProperties(estado, estadoAtualizar.get(), "id");
            return estadoRepository.save(estado);
        }
    }

    public void excluir (Long Id){
        try{
            Optional<Estado> estadoAtualizar = estadoRepository.findById(Id);
            if(estadoAtualizar.isPresent()){
                estadoRepository.delete(estadoAtualizar.get());
            }
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Estado de ID %d nn pode ser removido pois esta sendo usado"));
        }
    }


}