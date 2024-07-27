package org.pedroamorim.projetobootcamp.domain.dtos;

import lombok.Value;
import org.pedroamorim.projetobootcamp.domain.model.FormaPagamento;

import java.io.Serializable;

/**
 * DTO for {@link FormaPagamento}
 */
@Value
public class FormaPagamentoDto implements Serializable {
    Long id;
    String name;
    String descricao;
}