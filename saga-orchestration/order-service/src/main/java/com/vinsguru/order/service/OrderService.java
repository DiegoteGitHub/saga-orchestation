package com.vinsguru.order.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinsguru.dto.AddPriceReqRespDTO;
import com.vinsguru.dto.OrchestratorRequestDTO;
import com.vinsguru.dto.OrderRequestDTO;
import com.vinsguru.dto.OrderResponseDTO;
import com.vinsguru.enums.OrderStatus;
import com.vinsguru.enums.ProductPriceStatus;
import com.vinsguru.order.entity.ProductPrice;
import com.vinsguru.order.entity.PurchaseOrder;
import com.vinsguru.order.repository.ProductPriceRepository;
import com.vinsguru.order.repository.PurchaseOrderRepository;

import reactor.core.publisher.FluxSink;

@Service
public class OrderService {

	// product price map
//    private static final Map<Integer, Double> PRODUCT_PRICE =  Map.of(
//            1, 100d,
//            2, 200d,
//            3, 300d
//    );

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;

	@Autowired
	private ProductPriceRepository productPriceRepo;

	@Autowired
	private FluxSink<OrchestratorRequestDTO> sink;

	public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		Optional<ProductPrice> optionalProductPrice = productPriceRepo.findPriceByProductId(orderRequestDTO.getProductId());
		if (optionalProductPrice.isPresent()) {
			ProductPrice productPrice = optionalProductPrice.get();			
			purchaseOrder = this.purchaseOrderRepo.save(this.dtoToEntity(orderRequestDTO, productPrice));
			this.sink.next(this.getOrchestratorRequestDTO(orderRequestDTO, productPrice));
		} else {
			purchaseOrder = this.dtoToEntity(orderRequestDTO, null);
		}
		return purchaseOrder;
	}

	public List<OrderResponseDTO> getAll() {
		return this.purchaseOrderRepo.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
	}
	
	public List<AddPriceReqRespDTO> getAllPrices() {
		return this.productPriceRepo.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
	}

	private PurchaseOrder dtoToEntity(final OrderRequestDTO dto, final ProductPrice price) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setId(dto.getOrderId());
		purchaseOrder.setProductId(dto.getProductId());
		purchaseOrder.setUserId(dto.getUserId());
		if (price != null) {			
			purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
			purchaseOrder.setPrice(price.getUnitPrice());
		} else {
			purchaseOrder.setStatus(OrderStatus.ORDER_CANCELLED);
			purchaseOrder.setError(ProductPriceStatus.INEXISTENT_PRICE.toString());
		}
		return purchaseOrder;
	}
	
	private AddPriceReqRespDTO entityToDto(final ProductPrice productPrice) {
		AddPriceReqRespDTO dto = new AddPriceReqRespDTO();
		dto.setProductId(productPrice.getProductId());
		dto.setUnitPrice(productPrice.getUnitPrice());
		return dto;
	}

	private OrderResponseDTO entityToDto(final PurchaseOrder purchaseOrder) {
		OrderResponseDTO dto = new OrderResponseDTO();
		dto.setOrderId(purchaseOrder.getId());
		dto.setProductId(purchaseOrder.getProductId());
		dto.setUserId(purchaseOrder.getUserId());
		dto.setStatus(purchaseOrder.getStatus());
		dto.setAmount(purchaseOrder.getPrice());
		return dto;
	}

	public OrchestratorRequestDTO getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO, ProductPrice productPrice) {
		OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
		requestDTO.setUserId(orderRequestDTO.getUserId());
		requestDTO.setAmount(productPrice.getUnitPrice());
		requestDTO.setOrderId(orderRequestDTO.getOrderId());
		requestDTO.setProductId(orderRequestDTO.getProductId());
		return requestDTO;
	}

	public AddPriceReqRespDTO addProductPrice(AddPriceReqRespDTO reqResDTO) {
		Optional<ProductPrice> optionalProductPrice = productPriceRepo.findPriceByProductId(reqResDTO.getProductId());
		if (optionalProductPrice.isEmpty()) {
			reqResDTO.setStatus(ProductPriceStatus.PRICE_SAVED);
			productPriceRepo.save(new ProductPrice(reqResDTO.getProductId(), reqResDTO.getUnitPrice()));
		} else {
			reqResDTO.setStatus(ProductPriceStatus.PRICE_ALREADY_EXISTS);
		}
		return reqResDTO;
	}

}
