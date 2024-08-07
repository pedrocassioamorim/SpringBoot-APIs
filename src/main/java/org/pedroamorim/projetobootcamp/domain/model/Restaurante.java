package org.pedroamorim.projetobootcamp.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Restaurante{


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @Column(nullable = false)
    private String nome;


    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;


    @ManyToOne @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(taxaFrete, that.taxaFrete) && Objects.equals(cozinha, that.cozinha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, taxaFrete, cozinha);
    }
}
