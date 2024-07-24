package org.pedroamorim.projetobootcamp.repositories;

import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {



}
