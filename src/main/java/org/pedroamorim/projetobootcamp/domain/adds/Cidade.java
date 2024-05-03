package org.pedroamorim.projetobootcamp.domain.adds;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}
