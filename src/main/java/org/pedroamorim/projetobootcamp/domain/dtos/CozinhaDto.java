package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;

import java.io.Serializable;

/**
 * DTO for {@link Cozinha}
 */
@Value
public class CozinhaDto implements Serializable {
    Long id;
    String nome;
}