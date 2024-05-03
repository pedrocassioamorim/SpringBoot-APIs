package org.pedroamorim.projetobootcamp.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String descricao;

}
