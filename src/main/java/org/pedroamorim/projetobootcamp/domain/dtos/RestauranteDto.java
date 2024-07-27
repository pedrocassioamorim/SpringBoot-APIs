package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Restaurante}
 */
@Value
public class RestauranteDto implements Serializable {
    Long id;
    String nome;
    BigDecimal taxaFrete;
    CozinhaDto cozinha;
}