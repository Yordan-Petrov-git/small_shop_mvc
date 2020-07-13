package com.shop.advance.academy.yordan.petrov.git.shop.data.repository;

import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    Optional<Opinion> findById(Long id);

}