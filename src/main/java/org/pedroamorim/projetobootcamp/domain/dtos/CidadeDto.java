package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.Cidade;

import java.io.Serializable;

/**
 * DTO for {@link Cidade}
 */
@Value @Data
public class CidadeDto implements Serializable {
    Long id;
    String nome;
    EstadoDto estado;
}