package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.repositories.CozinhaRepository;
import org.pedroamorim.projetobootcamp.repositories.RestauranteRepository;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    public Restaurante buscar(Long Id){
        Optional<Restaurante> restaurante = restauranteRepository.findById(Id);
        if (restaurante.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um Restaurante com o ID %d", Id));
        }
        return restaurante.get();
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
        if (cozinha.isEmpty()){
            throw new RequisicaoRuimException(String.format("Não existe uma cozinha para o ID %d", cozinhaId));
        }
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long Id, Restaurante restaurante){
        Optional<Restaurante> restauranteAtualizar = restauranteRepository.findById(Id);
        if (restauranteAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um Restaurante com o ID %d", Id));
        } else {
            BeanUtils.copyProperties(restaurante, restauranteAtualizar.get(), "id");
            return salvar(restaurante);
        }
    }

    public void excluir (Long Id){
        try{
            restauranteRepository.deleteById(Id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um Restaurante com o ID %d", Id));
        }catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Não pode excluir Restaurante de codigo %d pois esta em uso"));
        }
    }


}
