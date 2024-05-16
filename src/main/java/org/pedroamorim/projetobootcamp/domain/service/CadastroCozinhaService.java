package org.pedroamorim.projetobootcamp.domain.service;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    public Cozinha buscar(Long Id){
        Cozinha cozinha = cozinhaRepository.buscar(Id);
        if(cozinha == null){
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o ID %d nao foi encontrada."));
        }
        return cozinha;
    }

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    public Cozinha atualizar(Long Id, Cozinha cozinha){
        Cozinha cozinhaAtualizar = cozinhaRepository.buscar(Id);
        if(cozinhaAtualizar == null){
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com o codigo %d", Id));
        } else{
            BeanUtils.copyProperties(cozinha, cozinhaAtualizar, "id");
            return cozinhaRepository.salvar(cozinhaAtualizar);
        }
    }

    public void excluir (Long id){
        try {
            cozinhaRepository.remover(id);
        } catch (EmptyResultDataAccessException f) {
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com o codigo %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d nn pode ser removida, pois esta em uso.", id));
        }

    }

}
