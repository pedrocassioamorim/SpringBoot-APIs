package org.pedroamorim.projetobootcamp.domain.repository;

import org.pedroamorim.projetobootcamp.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();

    Cidade buscar(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Long Id);

}
