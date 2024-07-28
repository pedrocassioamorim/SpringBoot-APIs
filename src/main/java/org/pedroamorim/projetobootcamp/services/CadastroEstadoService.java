package org.pedroamorim.projetobootcamp.services;

import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.EstadoDto;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.repositories.EstadoRepository;
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
public class CadastroEstadoService {

    public static final String ESTADO_DE_ID_D_NAO_ENCONTRADO = "Estado de ID %d nao encontrado";
    public static final String ESTADO_DE_ID_D_NN_PODE_SER_REMOVIDO_POIS_ESTA_SENDO_USADO = "Estado de ID %d nn pode ser removido pois esta sendo usado";

    @Autowired
    private EstadoRepository estadoRepository;

    public List<EstadoDto> listar(){
        ModelMapper modelMapper = new ModelMapper();
        List<Estado> estados = estadoRepository.findAll();
        return estados.stream().map(estado -> modelMapper.map(estado, EstadoDto.class)).collect(Collectors.toList());
    }


    public EstadoDto buscar(Long Id){
        ModelMapper modelMapper = new ModelMapper();
        Optional<Estado> estado = estadoRepository.findById(Id);
        if(estado.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(ESTADO_DE_ID_D_NAO_ENCONTRADO, Id));
        }
        return modelMapper.map(estado.get(), EstadoDto.class);
    }

    public EstadoDto salvar(EstadoDto estadoDto){
        ModelMapper modelMapper = new ModelMapper();
        estadoRepository.save(modelMapper.map(estadoDto, Estado.class));
        return estadoDto;
    }

    public EstadoDto atualizar(Long Id, EstadoDto estadoDto){
        ModelMapper modelMapper = new ModelMapper();
        Optional<Estado> estadoAtualizar = estadoRepository.findById(Id);
        if (estadoAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format(ESTADO_DE_ID_D_NAO_ENCONTRADO, Id));
        }
        BeanUtils.copyProperties(estadoDto, estadoAtualizar.get(), "id");
        estadoRepository.save(estadoAtualizar.get());
        return modelMapper.map(estadoAtualizar.get(), EstadoDto.class);
    }

    public void excluir (Long Id){
        try{
            Optional<Estado> estadoAtualizar = estadoRepository.findById(Id);
            estadoAtualizar.ifPresent(estado -> estadoRepository.delete(estado));
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(ESTADO_DE_ID_D_NAO_ENCONTRADO, Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(ESTADO_DE_ID_D_NN_PODE_SER_REMOVIDO_POIS_ESTA_SENDO_USADO, Id));
        }
    }


}
