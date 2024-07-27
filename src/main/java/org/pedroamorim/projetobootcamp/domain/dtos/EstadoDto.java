package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.Estado;

import java.io.Serializable;

/**
 * DTO for {@link Estado}
 */
@Value
public class EstadoDto implements Serializable {
    Long id;
    String nome;
}