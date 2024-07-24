package org.pedroamorim.projetobootcamp.services;


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

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento buscar(Long Id){
        Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(Id);
        if (formaPagamento.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        }
        return formaPagamento.get();
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento atualizar(Long Id, FormaPagamento formaPagamento){
        Optional<FormaPagamento> formaPagamentoAtualizar = formaPagamentoRepository.findById(Id);
        if (formaPagamentoAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        } else {
            BeanUtils.copyProperties(formaPagamento, formaPagamentoAtualizar.get(), "id");
            return formaPagamentoRepository.save(formaPagamento);
        }
    }

    public void excluir (Long Id){
        try{
            formaPagamentoRepository.deleteById(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Forma de Pagamento de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }

}
