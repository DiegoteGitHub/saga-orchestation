package com.vinsguru.order.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.order.entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, UUID> {
}
