package org.pedroamorim.projetobootcamp.domain.adds;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Estado {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;


}
