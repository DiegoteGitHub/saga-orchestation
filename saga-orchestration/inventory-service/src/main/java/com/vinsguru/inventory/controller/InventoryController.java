package com.vinsguru.inventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.dto.InventoryAddProdRequestDTO;
import com.vinsguru.dto.InventoryRequestDTO;
import com.vinsguru.dto.InventoryResponseDTO;
import com.vinsguru.inventory.service.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/deduct")
    public InventoryResponseDTO deduct(@RequestBody final InventoryRequestDTO requestDTO){
        return this.service.deductInventory(requestDTO);
    }

    @PostMapping("/add")
    public InventoryResponseDTO add(@RequestBody final InventoryRequestDTO requestDTO){
        return this.service.addInventory(requestDTO);
    }
    
    @PostMapping("/create")
    public InventoryResponseDTO create(@Valid @RequestBody final InventoryAddProdRequestDTO requestDTO){
        return this.service.addNewProduct(requestDTO);
    }
    
    @GetMapping("/all")
    public List<InventoryResponseDTO> getAllProducts() {
    	return this.service.getAllProducts();
    }

}
