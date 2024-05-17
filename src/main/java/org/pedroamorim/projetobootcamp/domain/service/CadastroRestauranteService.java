package org.pedroamorim.projetobootcamp.domain.service;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.exceptions.RequisicaoRuimException;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.domain.repository.CozinhaRepository;
import org.pedroamorim.projetobootcamp.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public List<Restaurante> listar(){
        return restauranteRepository.listar();
    }

    public Restaurante buscar(Long Id){
        Restaurante restaurante = restauranteRepository.buscar(Id);
        if (restaurante == null){
            throw new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um Restaurante com o ID %d", Id));
        }
        return restaurante;
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        if (cozinha == null){
            throw new RequisicaoRuimException(String.format("Não existe uma cozinha para o ID %d", cozinhaId));
        }
        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizar(Long Id, Restaurante restaurante){
        Restaurante restauranteAtualizar = restauranteRepository.buscar(Id);
        if (restauranteAtualizar == null){
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um Restaurante com o ID %d", Id));
        } else {
            BeanUtils.copyProperties(restaurante, restauranteAtualizar, "id");
            return salvar(restauranteAtualizar);
        }
    }

    public void excluir (Long Id){
        try{
            restauranteRepository.remover(Id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um Restaurante com o ID %d", Id));
        }catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Não pode excluir Restaurante de codigo %d pois esta em uso"));
        }
    }


}
