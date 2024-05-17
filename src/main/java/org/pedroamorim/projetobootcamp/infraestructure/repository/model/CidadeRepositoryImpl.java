package org.pedroamorim.projetobootcamp.infraestructure.repository.model;

import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.pedroamorim.projetobootcamp.domain.repository.CidadeRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        return manager.createQuery("from Cidade", Cidade.class).getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    public Cidade salvar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    public void remover(Long Id) {
        Cidade c = manager.find(Cidade.class, Id);
        manager.remove(c);
    }
}
