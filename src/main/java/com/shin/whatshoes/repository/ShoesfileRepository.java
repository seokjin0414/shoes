package com.shin.whatshoes.repository;

import com.shin.whatshoes.model.Shoesfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesfileRepository extends JpaRepository<Shoesfile, Long> {
    Shoesfile findByFileShoesShoesId(Long shoesId);
}
