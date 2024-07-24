package org.pedroamorim.projetobootcamp.repositories;

import org.pedroamorim.projetobootcamp.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
