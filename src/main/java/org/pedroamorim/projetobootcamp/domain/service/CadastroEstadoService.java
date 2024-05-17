package org.pedroamorim.projetobootcamp.domain.service;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.listar();
    }

    public Estado buscar(Long Id){
        Estado estado = estadoRepository.buscar(Id);
        if(estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        }
        return estado;
    }

    public Estado salvar(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public Estado atualizar(Long Id, Estado estado){
        Estado estadoAtualizar = estadoRepository.buscar(Id);
        if (estadoAtualizar == null) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        } else {
            BeanUtils.copyProperties(estado, estadoAtualizar, "id");
            return estadoRepository.salvar(estadoAtualizar);
        }
    }

    public void excluir (Long Id){
        try{
            estadoRepository.remover(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Estado de ID %d nao encontrado", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Estado de ID %d nn pode ser removido pois esta sendo usado"));
        }
    }


}
