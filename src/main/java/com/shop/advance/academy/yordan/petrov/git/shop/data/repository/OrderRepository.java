package com.shop.advance.academy.yordan.petrov.git.shop.data.repository;

import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    Optional<Order> findByNumber(String orderNumber);

}
