package com.shin.whatshoes.repository;


import com.shin.whatshoes.model.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ShoesRepository extends JpaRepository<Shoes, Long>, QuerydslPredicateExecutor<Shoes> {

    @EntityGraph(attributePaths = {"user"})
    Shoes findByShoesId(Long shoesId);

    List<Shoes> findByShoesName1(String shoesName1);

    List<Shoes> findByShoesName1OrModelId(String shoesName1, String ModelId);

    @EntityGraph(attributePaths = {"user"})
    Page<Shoes> findByShoesStyleContainingOrBrandIdContainingOrModelIdContainingOrShoesName1ContainingOrShoesName2ContainingOrShoesColorwayContaining(String shoesStyle, String brandId, String modelId, String shoesName1, String shoesName2, String shoesColorway, Pageable pageable);
}
