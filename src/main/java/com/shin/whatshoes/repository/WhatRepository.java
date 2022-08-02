package com.shin.whatshoes.repository;


import com.shin.whatshoes.model.What;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatRepository extends JpaRepository<What, Long> {
}
