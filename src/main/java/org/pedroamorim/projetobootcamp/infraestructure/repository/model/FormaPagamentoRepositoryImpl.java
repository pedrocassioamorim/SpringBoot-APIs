package org.pedroamorim.projetobootcamp.infraestructure.repository.model;

import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;
import org.pedroamorim.projetobootcamp.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<FormaPagamento> listar() {
        return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return manager.find(FormaPagamento.class, id);
    }

    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return manager.merge(formaPagamento);
    }


    @Override
    public void deletar(Long id) {
        FormaPagamento formaPagamento = manager.find(FormaPagamento.class, id);
        manager.remove(formaPagamento);
    }
}
