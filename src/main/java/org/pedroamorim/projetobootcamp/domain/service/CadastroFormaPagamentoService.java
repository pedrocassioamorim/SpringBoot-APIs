package org.pedroamorim.projetobootcamp.domain.service;


import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;
import org.pedroamorim.projetobootcamp.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.listar();
    }

    public FormaPagamento buscar(Long Id){
        FormaPagamento formaPagamento = formaPagamentoRepository.buscar(Id);
        if (formaPagamento == null){
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        }
        return formaPagamento;
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return formaPagamentoRepository.salvar(formaPagamento);
    }

    public FormaPagamento atualizar(Long Id, FormaPagamento formaPagamento){
        FormaPagamento formaPagamentoAtualizar = formaPagamentoRepository.buscar(Id);
        if (formaPagamentoAtualizar == null) {
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        } else {
            BeanUtils.copyProperties(formaPagamento, formaPagamentoAtualizar, "id");
            return formaPagamentoRepository.salvar(formaPagamentoAtualizar);
        }
    }

    public void excluir (Long Id){
        try{
            formaPagamentoRepository.deletar(Id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Forma de Pagamento de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }

}
