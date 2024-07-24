package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Permissao;
import org.pedroamorim.projetobootcamp.repositories.PermissaoRepository;
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
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;


    public List<Permissao> listar(){
        return permissaoRepository.findAll();
    }


    public Permissao buscar (Long Id){
        Optional<Permissao> permissao = permissaoRepository.findById(Id);
        if (permissao.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        }
        return permissao.get();
    }

    public Permissao salvar (Permissao permissao){
        return permissaoRepository.save(permissao);
    }

    public Permissao atualizar (Permissao permissao, Long Id){
        Optional<Permissao> permissaoAtualizar = permissaoRepository.findById(Id);
        if (permissaoAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        } else {
            BeanUtils.copyProperties(permissao, permissaoAtualizar.get(), "id");
            return permissaoRepository.save(permissao);
        }
    }

    public void excluir (Long Id){
        try{
            permissaoRepository.deleteById(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Permissao de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Permissao de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }



}
