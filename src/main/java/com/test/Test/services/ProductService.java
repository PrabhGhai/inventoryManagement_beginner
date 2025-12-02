package com.test.Test.services;
import DTOS.ProductDTO;
import com.test.Test.models.Product;
import com.test.Test.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    ProductRepo productRepo;

    public void addproduct(ProductDTO prodDto)
    {
        if(prodDto.getName().isEmpty() || prodDto.getPrice()==0 || prodDto.getStock()==0)
        {
            throw new IllegalArgumentException("All fields are required");
        }

        Product newProduct = new Product();
        newProduct.setName(prodDto.getName());
        newProduct.setPrice(prodDto.getPrice());
        newProduct.setStock(prodDto.getStock());
        productRepo.save(newProduct);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());
            productDTOs.add(productDTO);
        } );

        return productDTOs;
    }

    public ProductDTO getProductById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;

    }
}
