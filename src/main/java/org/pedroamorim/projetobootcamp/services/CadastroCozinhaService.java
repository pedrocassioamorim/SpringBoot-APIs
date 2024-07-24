package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.repositories.CozinhaRepository;
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
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    public Cozinha buscar(Long Id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(Id);
        if(!cozinha.isPresent()){
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o ID %d nao foi encontrada."));
        }
        return cozinha.get();
    }

    public List<Cozinha> buscarPorNome(String nome){
        List<Cozinha> cozinhas = cozinhaRepository.findByNomeContaining(nome);
        if(cozinhas == null){
            throw new EntidadeNaoEncontradaException("Cozinha não encontrada.");
        }
        return cozinhas;
    }

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha atualizar(Long Id, Cozinha cozinha){
        Optional<Cozinha> cozinhaAtualizar = cozinhaRepository.findById(Id);

        if(cozinhaAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com o codigo %d", Id));
        } else{
            BeanUtils.copyProperties(cozinha, cozinhaAtualizar.get(), "id");
            return cozinhaRepository.save(cozinha);
        }
    }

    public void excluir (Long id){
        try {
            Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
            if(cozinha.isPresent()){
                cozinhaRepository.delete(cozinha.get());
            }
        } catch (EmptyResultDataAccessException f) {
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com o codigo %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d nn pode ser removida, pois esta em uso.", id));
        }

    }

}
