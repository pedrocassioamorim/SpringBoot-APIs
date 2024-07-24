package org.pedroamorim.projetobootcamp.repositories;

import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
