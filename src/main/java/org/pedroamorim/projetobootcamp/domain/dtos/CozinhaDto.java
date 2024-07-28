package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;

import java.io.Serializable;

/**
 * DTO for {@link Cozinha}
 */
@Data
public class CozinhaDto implements Serializable {
    Long id;
    String nome;
}