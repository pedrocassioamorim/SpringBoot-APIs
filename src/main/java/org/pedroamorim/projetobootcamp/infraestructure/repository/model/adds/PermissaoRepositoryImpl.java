package org.pedroamorim.projetobootcamp.infraestructure.repository.adds;

import org.pedroamorim.projetobootcamp.domain.adds.Permissao;
import org.pedroamorim.projetobootcamp.domain.repository.adds.PermissaoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> listar() {
        return manager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    public Permissao buscar(Long id) {
        return manager.find(Permissao.class, id);
    }

    @Override
    public Permissao salvar(Permissao permissao) {
        return manager.merge(permissao);
    }

    @Override
    public void remover(Long id) {
        Permissao permissao = buscar(id);
        manager.remove(permissao);
    }
}
