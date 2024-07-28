package org.pedroamorim.projetobootcamp.services;


import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.FormaPagamentoDto;
import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;
import org.pedroamorim.projetobootcamp.repositories.FormaPagamentoRepository;
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
public class CadastroFormaPagamentoService {

    public static final String FORMA_DE_PAGAMENTO_DE_ID_D_NAO_ENCONTRADA = "Forma de Pagamento de ID %d nao encontrada";
    public static final String FORMA_DE_PAGAMENTO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA = "Forma de Pagamento de ID %d nao pode ser removida pois esta sendo usada";
    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    public List<FormaPagamentoDto> listar(){
        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
        return formaPagamentos.stream().map(formaPagamento -> modelMapper.map(formaPagamento, FormaPagamentoDto.class)).collect(Collectors.toList());
    }

    public FormaPagamentoDto buscar(Long Id){
        Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(Id);
        if (formaPagamento.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(FORMA_DE_PAGAMENTO_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        return modelMapper.map(formaPagamento.get(), FormaPagamentoDto.class);
    }

    public FormaPagamentoDto salvar(FormaPagamentoDto formaPagamentoDto){
        formaPagamentoRepository.save(modelMapper.map(formaPagamentoDto, FormaPagamento.class));
        return formaPagamentoDto;
    }

    public FormaPagamentoDto atualizar(Long Id, FormaPagamentoDto formaPagamentoDto){
        Optional<FormaPagamento> formaPagamentoAtualizar = formaPagamentoRepository.findById(Id);
        if (formaPagamentoAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format(FORMA_DE_PAGAMENTO_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        BeanUtils.copyProperties(formaPagamentoDto, formaPagamentoAtualizar.get(), "id");
        formaPagamentoRepository.save(formaPagamentoAtualizar.get());
        return modelMapper.map(formaPagamentoAtualizar.get(), FormaPagamentoDto.class);
    }

    public void excluir (Long Id){
        try{
            Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(Id);
            formaPagamento.ifPresent(forma -> formaPagamentoRepository.delete(forma));
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(FORMA_DE_PAGAMENTO_DE_ID_D_NAO_ENCONTRADA, Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(FORMA_DE_PAGAMENTO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA, Id));
        }
    }

}
