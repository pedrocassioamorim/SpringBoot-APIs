package org.pedroamorim.projetobootcamp.domain.service;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.exceptions.RequisicaoRuimException;
import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.domain.repository.CidadeRepository;
import org.pedroamorim.projetobootcamp.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {


    @Autowired
    private CidadeRepository cidadeRepository;


    @Autowired
    private EstadoRepository estadoRepository;


    public List<Cidade> listar(){
        return cidadeRepository.listar();
    }

    public Cidade buscar(Long Id){
        Cidade cidade = cidadeRepository.buscar(Id);
        if (cidade == null){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
        }
        return cidade;
    }

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);
        if (estado == null){
            throw new RequisicaoRuimException(String.format("Não existe um Estado com o ID %d", estadoId));
        }
        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public Cidade atualizar (Long Id, Cidade cidade){
        Cidade cidadeAtualizar = cidadeRepository.buscar(Id);
        if (cidadeAtualizar == null){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
        } else {
            BeanUtils.copyProperties(cidade, cidadeAtualizar, "id");
            return salvar(cidadeAtualizar);
        }
    }

    public void excluir (Long Id){
        try{
            cidadeRepository.remover(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Cidade de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }

}
