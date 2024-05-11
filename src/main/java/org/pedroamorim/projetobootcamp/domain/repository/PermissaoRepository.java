package org.pedroamorim.projetobootcamp.domain.repository;

import org.pedroamorim.projetobootcamp.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {
    List<Permissao> listar();

    Permissao buscar(Long id);

    Permissao salvar(Permissao permissao);

    void remover(Long id);
}
