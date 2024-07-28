package org.pedroamorim.projetobootcamp.services;

import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.CozinhaDto;
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
import java.util.stream.Collectors;

@Service
public class CadastroCozinhaService {

    public static final String COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA = "Cozinha com o ID %d nao foi encontrada.";
    public static final String COZINHA_NAO_ENCONTRADA_NOME = "Cozinha não encontrada pelo nome: ";
    public static final String COZINHA_DE_CODIGO_D_NN_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO = "Cozinha de codigo %d nn pode ser removida, pois esta em uso.";
    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<CozinhaDto> listar(){
        List<Cozinha> cozinhas = cozinhaRepository.findAll();
        return cozinhas.stream().map(cozinha -> modelMapper.map(cozinha, CozinhaDto.class)).collect(Collectors.toList());
    }

    public List<CozinhaDto> listarPorNome(String nome){
        List<Cozinha> cozinhas = cozinhaRepository.findTodasByNomeContaining(nome);
        return cozinhas.stream().map(cozinha -> modelMapper.map(cozinha, CozinhaDto.class)).collect(Collectors.toList());
    }

    public CozinhaDto findUnicoByNome(String nome){
        Optional<Cozinha> cozinha = cozinhaRepository.findUniqueByNome(nome);
        if(cozinha.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_NAO_ENCONTRADA_NOME));
        }
        return modelMapper.map(cozinha.get(), CozinhaDto.class);
    }

    public boolean existsByName(String nome){
        return cozinhaRepository.existsByNome(nome);
    }

    public CozinhaDto findById(Long Id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(Id);
        if(cozinha.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA));
        }
        return modelMapper.map(cozinha.get(), CozinhaDto.class);
    }


    public CozinhaDto salvar(CozinhaDto cozinhaDto){
        Cozinha cozinha = modelMapper.map(cozinhaDto, Cozinha.class);
        cozinhaRepository.save(cozinha);
        return modelMapper.map(cozinha, CozinhaDto.class);
    }

    public CozinhaDto atualizar(Long Id, CozinhaDto cozinhaDto){
        Optional<Cozinha> cozinhaAtualizar = cozinhaRepository.findById(Id);
        if(cozinhaAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA, Id));
        }
        Cozinha cozinha = cozinhaAtualizar.get();
        BeanUtils.copyProperties(cozinhaDto, cozinha, "id");
        cozinhaRepository.save(cozinha);
        return modelMapper.map(cozinha, CozinhaDto.class);

    }

    public void excluir (Long id){
        try {
            Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
            cozinha.ifPresent(value -> cozinhaRepository.delete(value));
        } catch (EmptyResultDataAccessException f) {
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_ID_D_NAO_FOI_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(COZINHA_DE_CODIGO_D_NN_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO, id));
        }

    }

}
