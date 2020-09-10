package com.vinsguru.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinsguru.dto.InventoryAddProdRequestDTO;
import com.vinsguru.dto.InventoryRequestDTO;
import com.vinsguru.dto.InventoryResponseDTO;
import com.vinsguru.enums.InventoryStatus;
import com.vinsguru.inventory.entity.Product;
import com.vinsguru.inventory.repository.ProductRepository;

@Service
public class InventoryService {

	@Autowired
	private ProductRepository repository;

	@PostConstruct
	private void init() {
//    	repository.deleteAll();
		// save a couple of products
//		repository.save(new Product(1,"Soap", 3));
//		repository.save(new Product(2, "Broom", 6));
	}

	public InventoryResponseDTO deductInventory(final InventoryRequestDTO requestDTO) {
		Optional<Product> optProd = repository.findByIdCode(requestDTO.getProductId());
		InventoryResponseDTO responseDTO = new InventoryResponseDTO();
		responseDTO.setStatus(InventoryStatus.UNAVAILABLE);
		if (optProd.isPresent()) {
			Product p = optProd.get();
			responseDTO.setOrderId(requestDTO.getOrderId());
			responseDTO.setUserId(requestDTO.getUserId());
			responseDTO.setProductId(requestDTO.getProductId());
			if (p.getQuantity() > 0) {
				responseDTO.setStatus(InventoryStatus.AVAILABLE);
				p.setQuantity(p.getQuantity() - 1);
				repository.save(p);
			}
		}
		return responseDTO;
	}

	public InventoryResponseDTO addInventory(final InventoryRequestDTO requestDTO) {
		Optional<Product> optProd = repository.findByIdCode(requestDTO.getProductId());
		InventoryResponseDTO responseDTO = new InventoryResponseDTO();
		if (optProd.isPresent()) {
			Product p = optProd.get();
			if (requestDTO.getQuantity() > 0) {
				p.setQuantity(p.getQuantity() + requestDTO.getQuantity());
			} else {				
				p.setQuantity(p.getQuantity() + 1);
			}
			repository.save(p);
			responseDTO.setProductId(requestDTO.getProductId());
			responseDTO.setStatus(InventoryStatus.ADDED);
		} else {
			responseDTO.setProductId(requestDTO.getProductId());
			responseDTO.setStatus(InventoryStatus.INEXISTENT);
		}
		return responseDTO;
	}

	public InventoryResponseDTO addNewProduct(InventoryAddProdRequestDTO requestDTO) {
		InventoryResponseDTO responseDTO = new InventoryResponseDTO();
		Optional<Product> optProd = repository.findByIdCode(requestDTO.getProductId());
		if (optProd.isEmpty()) {
			Integer quantity = requestDTO.getQuantity() != null ? requestDTO.getQuantity() : 0;
			repository.save(new Product(requestDTO.getProductId(), requestDTO.getName(), quantity));
			responseDTO.setProductId(requestDTO.getProductId());
			responseDTO.setStatus(InventoryStatus.ADDED);
		} else {
			responseDTO.setProductId(requestDTO.getProductId());
			responseDTO.setStatus(InventoryStatus.ALREADYEXISTS);
			responseDTO.setName(requestDTO.getName());
		}
		return responseDTO;
	}

	public List<InventoryResponseDTO> getAllProducts() {
		List<Product> products = repository.findAll();
		List<InventoryResponseDTO> allProducts = new ArrayList<>();
		for (Product product : products) {
			InventoryResponseDTO respProd = new InventoryResponseDTO();
			respProd.setProductId(product.getIdCode());
			respProd.setName(product.getName());
			respProd.setQuantity(product.getQuantity());
			allProducts.add(respProd);
		}
		return allProducts;
	}

}
