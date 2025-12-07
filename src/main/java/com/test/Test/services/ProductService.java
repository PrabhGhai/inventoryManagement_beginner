package com.test.Test.services;
import DTOS.ProductDTO;
import com.test.Test.models.Product;
import com.test.Test.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    ProductRepo productRepo;

    public void addproduct(ProductDTO prodDto)
    {
        if(prodDto.getName().isEmpty() || prodDto.getPrice()==0 || prodDto.getDescription().isEmpty() || prodDto.getStock()==0)
        {
            throw new IllegalArgumentException("All fields are required");
        }

        Product newProduct = new Product();
        newProduct.setName(prodDto.getName());
        newProduct.setPrice(prodDto.getPrice());
        newProduct.setDescription(prodDto.getDescription());
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

    public ResponseEntity<?> searchProduct(String name) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        List<Product> products;
        System.out.println(name);
        products = productRepo.findByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(name,name);
        if(products.isEmpty())
        {
            return ResponseEntity.badRequest().body("Product not found");
        }

        for(Product product:products)
        {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());
            productDTO.setDescription(product.getDescription());
            productDTOs.add(productDTO);
        }
        return ResponseEntity.ok(productDTOs);
    }
}
