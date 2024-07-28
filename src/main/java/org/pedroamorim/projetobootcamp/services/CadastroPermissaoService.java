package org.pedroamorim.projetobootcamp.services;

import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.PermissaoDto;
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
import java.util.stream.Collectors;

@Service
public class CadastroPermissaoService {

    public static final String PERMISSAO_DE_ID_D_NAO_ENCONTRADA = "Permissao de ID %d nao encontrada";
    public static final String PERMISSAO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA = "Permissao de ID %d nao pode ser removida pois esta sendo usada";
    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PermissaoRepository permissaoRepository;


    public List<PermissaoDto> listar(){
        List<Permissao> permissaos = permissaoRepository.findAll();
        return permissaos.stream().map(permissao -> modelMapper.map(permissao, PermissaoDto.class)).collect(Collectors.toList());
    }


    public PermissaoDto buscar (Long Id){
        Optional<Permissao> permissao = permissaoRepository.findById(Id);
        if (permissao.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(PERMISSAO_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        return modelMapper.map(permissao.get(), PermissaoDto.class);
    }

    public PermissaoDto salvar (PermissaoDto permissaoDto){
        permissaoRepository.save(modelMapper.map(permissaoDto, Permissao.class));
        return permissaoDto;
    }

    public PermissaoDto atualizar (PermissaoDto permissaoDto, Long Id){
        Optional<Permissao> permissaoAtualizar = permissaoRepository.findById(Id);
        if (permissaoAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(PERMISSAO_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        BeanUtils.copyProperties(permissaoDto, permissaoAtualizar.get(), "id");
        permissaoRepository.save(permissaoAtualizar.get());
        return modelMapper.map(permissaoAtualizar.get(), PermissaoDto.class);

    }

    public void excluir (Long Id){
        try{
            Permissao permissao = permissaoRepository.findById(Id).get();
            permissaoRepository.delete(permissao);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(PERMISSAO_DE_ID_D_NAO_ENCONTRADA, Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(PERMISSAO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA, Id));
        }
    }



}
