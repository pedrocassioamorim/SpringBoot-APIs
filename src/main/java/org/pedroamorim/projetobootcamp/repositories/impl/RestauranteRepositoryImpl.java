package org.pedroamorim.projetobootcamp.repositories.impl;


import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> consultaNomeEFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

//        IMPLEMENTAÇÃO DE CONSULTA DINAMICA
//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaFreteInicial ");
//            parametros.put("taxaFreteInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFreteFinal ");
//            parametros.put("taxaFreteFinal", taxaFreteFinal);
//        }
//       TypedQuery<Restaurante> query =  manager.createQuery(jpql.toString(), Restaurante.class);
//        parametros.forEach(query::setParameter);
//        return query.getResultList();
// ====================================================================================================================
//
//        IMPLEMENTAÇÃO DE CONSULTAS UTILIZANDO CRITERIA API

        // CRITERIA BUILDER - FABRICA DE CRITERIAS
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // CONSTRUTOR DE CLUSULAS
        CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class); // from Restaurante

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (taxaFreteFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }


        criteriaQuery.where(predicates.toArray(new Predicate[0]));


        // TRANSFORMANDO AS CLAUSULAS PARA UMA QUERY
        TypedQuery<Restaurante> query = manager.createQuery(criteriaQuery);
        return query.getResultList();


    }



}
