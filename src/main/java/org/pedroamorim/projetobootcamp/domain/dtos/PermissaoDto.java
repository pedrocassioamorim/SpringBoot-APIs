package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.Permissao;

import java.io.Serializable;

/**
 * DTO for {@link Permissao}
 */
@Value
public class PermissaoDto implements Serializable {
    Long id;
    String nome;
    String descricao;
}