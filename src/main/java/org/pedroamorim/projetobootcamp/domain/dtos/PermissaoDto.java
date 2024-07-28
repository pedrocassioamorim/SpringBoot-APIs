package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import org.pedroamorim.projetobootcamp.domain.model.Permissao;

import java.io.Serializable;

/**
 * DTO for {@link Permissao}
 */
@Data
public class PermissaoDto implements Serializable {
    Long id;
    String nome;
    String descricao;
}