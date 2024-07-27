package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.repositories.CozinhaRepository;
import org.pedroamorim.projetobootcamp.repositories.RestauranteRepository;
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

    public static final String COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA = "Cozinha com o ID %d nao foi encontrada.";
    public static final String COZINHA_NAO_ENCONTRADA_NOME = "Cozinha não encontrada pelo nome: ";
    public static final String COZINHA_DE_CODIGO_D_NN_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO = "Cozinha de codigo %d nn pode ser removida, pois esta em uso.";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    public List<Cozinha> listarPorNome(String nome){
        return cozinhaRepository.findByNome(nome);
    }

    public Optional<Cozinha> findUnicoByNome(String nome){
        return cozinhaRepository.findUnicoByNome(nome);
    }

    public Cozinha buscar(Long Id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(Id);
        if(cozinha.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA));
        }
        return cozinha.get();
    }


    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha atualizar(Long Id, Cozinha cozinha){
        Optional<Cozinha> cozinhaAtualizar = cozinhaRepository.findById(Id);

        if(cozinhaAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA, Id));
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
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(COZINHA_DE_CODIGO_D_NN_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO, id));
        }

    }

}
