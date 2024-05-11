package org.pedroamorim.projetobootcamp.domain.repository;

import org.pedroamorim.projetobootcamp.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado estado);

    void remover(Long id);
}
