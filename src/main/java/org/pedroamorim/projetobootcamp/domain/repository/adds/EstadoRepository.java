package org.pedroamorim.projetobootcamp.domain.repository.adds;

import org.pedroamorim.projetobootcamp.domain.model.adds.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado estado);

    void remover(Long id);
}
