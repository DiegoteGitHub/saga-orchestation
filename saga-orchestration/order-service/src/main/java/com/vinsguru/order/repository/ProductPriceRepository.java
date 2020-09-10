package com.vinsguru.order.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinsguru.order.entity.ProductPrice;

public interface ProductPriceRepository extends MongoRepository<ProductPrice, UUID> {

	public Optional<ProductPrice> findPriceByProductId(Integer productId);
	
}
