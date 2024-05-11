package org.pedroamorim.projetobootcamp.domain.repository.adds;

import org.pedroamorim.projetobootcamp.domain.model.adds.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();

    Cidade buscar(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Cidade cidade);
}
