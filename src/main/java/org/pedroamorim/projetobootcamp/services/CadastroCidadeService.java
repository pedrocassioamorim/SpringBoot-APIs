package org.pedroamorim.projetobootcamp.services;

import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.CidadeDto;
import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.repositories.CidadeRepository;
import org.pedroamorim.projetobootcamp.repositories.EstadoRepository;
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
import java.util.stream.Collectors;

@Service
public class CadastroCidadeService {


    public static final String CIDADE_DE_ID_D_NAO_ENCONTRADA = "Cidade de ID %d nao encontrada";
    public static final String NAO_EXISTE_UM_ESTADO_COM_O_ID_D = "Não existe um Estado com o ID %d";
    public static final String CIDADE_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA = "Cidade de ID %d nao pode ser removida pois esta sendo usada";


    @Autowired
    private CidadeRepository cidadeRepository;


    @Autowired
    private EstadoRepository estadoRepository;


    public List<CidadeDto> listar(){
        ModelMapper modelMapper = new ModelMapper();
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidades.stream()
                .map(cidade -> modelMapper.map(cidade, CidadeDto.class))
                .collect(Collectors.toList());
    }

    public CidadeDto buscar (Long Id){
        ModelMapper modelMapper = new ModelMapper();
        Optional<Cidade> cidade = cidadeRepository.findById(Id);
        if (cidade.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        return modelMapper.map(cidade.get(), CidadeDto.class);
    }

    public CidadeDto salvar(CidadeDto cidadeDto){
        ModelMapper modelMapper = new ModelMapper();
        Long estadoId = cidadeDto.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);
        if (estado.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(NAO_EXISTE_UM_ESTADO_COM_O_ID_D, estadoId));
        }
        Cidade cidade = cidadeRepository.save(modelMapper.map(cidadeDto, Cidade.class));
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public CidadeDto atualizar (Long Id, CidadeDto cidadeDto){
        ModelMapper modelMapper = new ModelMapper();
        Optional<Cidade> cidadeAtualizar = cidadeRepository.findById(Id);
        if (cidadeAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        Cidade cidade = cidadeAtualizar.get();
        BeanUtils.copyProperties(cidadeDto, cidade, "id");
        cidadeRepository.save(cidade);
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public void excluir (Long Id){
        try{
            Optional<Cidade> cidadeAtualizar = cidadeRepository.findById(Id);
            cidadeAtualizar.ifPresent(cidade -> cidadeRepository.delete(cidade));
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(CIDADE_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA, Id));
        }
    }

}
