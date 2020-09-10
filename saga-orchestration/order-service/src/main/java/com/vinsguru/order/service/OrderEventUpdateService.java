package com.vinsguru.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinsguru.dto.OrchestratorResponseDTO;
import com.vinsguru.order.repository.PurchaseOrderRepository;

@Service
public class OrderEventUpdateService {

    @Autowired
    private PurchaseOrderRepository repository;

    public void updateOrder(final OrchestratorResponseDTO responseDTO){
        this.repository
                .findById(responseDTO.getOrderId())
                .ifPresent(po -> {
                    po.setStatus(responseDTO.getStatus());
                    this.repository.save(po);
                });
    }

}
