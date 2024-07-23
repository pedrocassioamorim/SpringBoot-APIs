package org.pedroamorim.projetobootcamp.domain.repository;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();

    Cozinha buscar(Long id);

    List<Cozinha> buscarPorNome(String nome);

    Cozinha salvar(Cozinha cozinha);

    void remover(Long id);
}
