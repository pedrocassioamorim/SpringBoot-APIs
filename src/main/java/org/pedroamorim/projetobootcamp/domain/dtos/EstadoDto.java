package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import org.pedroamorim.projetobootcamp.domain.model.Estado;

import java.io.Serializable;

/**
 * DTO for {@link Estado}
 */
@Data
public class EstadoDto implements Serializable {
    Long id;
    String nome;
}