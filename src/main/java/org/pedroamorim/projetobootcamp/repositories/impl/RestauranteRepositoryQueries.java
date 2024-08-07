package org.pedroamorim.projetobootcamp.repositories.impl;

import org.pedroamorim.projetobootcamp.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> consultaNomeEFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
