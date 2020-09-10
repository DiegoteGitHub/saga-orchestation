package com.vinsguru.inventory.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinsguru.inventory.entity.Product;

public interface ProductRepository extends MongoRepository<Product, Integer> {

	Optional<Product> findByIdCode(Integer productIdCode);

}
