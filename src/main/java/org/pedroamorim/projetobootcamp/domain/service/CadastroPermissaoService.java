package org.pedroamorim.projetobootcamp.domain.service;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Permissao;
import org.pedroamorim.projetobootcamp.domain.repository.PermissaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;


    public List<Permissao> listar(){
        return permissaoRepository.listar();
    }

    public Permissao buscar (Long Id){
        Permissao permissao = permissaoRepository.buscar(Id);
        if (permissao == null){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        }
        return permissao;
    }

    public Permissao salvar (Permissao permissao){
        return permissaoRepository.salvar(permissao);
    }

    public Permissao atualizar (Permissao permissao, Long Id){
        Permissao permissaoAtualizar = permissaoRepository.buscar(Id);
        if (permissaoAtualizar == null){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        } else {
            BeanUtils.copyProperties(permissao, permissaoAtualizar, "id");
            return permissaoRepository.salvar(permissaoAtualizar);
        }
    }

    public void excluir (Long Id){
        try{
            permissaoRepository.remover(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Permissao de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }



}
