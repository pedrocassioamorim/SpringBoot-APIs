package org.pedroamorim.projetobootcamp.repositories;

import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {



}
