package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Data;
import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;

import java.io.Serializable;

/**
 * DTO for {@link FormaPagamento}
 */
@Data
public class FormaPagamentoDto implements Serializable {
    Long id;
    String name;
    String descricao;
}