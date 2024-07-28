package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Restaurante}
 */
@Data
public class RestauranteDto implements Serializable {
    Long id;
    String nome;
    BigDecimal taxaFrete;
    CozinhaDto cozinha;


}