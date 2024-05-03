package org.pedroamorim.projetobootcamp.domain.repository;

import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();

    FormaPagamento buscar (Long id);

    FormaPagamento salvar (FormaPagamento formaPagamento);

    void deletar(Long id);
}
