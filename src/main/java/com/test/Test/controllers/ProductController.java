package com.test.Test.controllers;
import DTOS.ProductDTO;
import com.test.Test.models.Product;
import com.test.Test.repos.ProductRepo;
import com.test.Test.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO prodDto) {
        try{
               productService.addproduct(prodDto);
               return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
        }
        catch(IllegalArgumentException e)
        {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<?> searchProduct(@RequestParam String name){
        try{
            return productService.searchProduct(name);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProducts")
    public ResponseEntity<Object> updateProducts(@RequestBody List<Map<Long,Integer>> products) {
        List<Product> outProduct = new ArrayList<>();
        for(Map<Long,Integer> mpp : products)
        {
            for(Map.Entry<Long,Integer> mp : mpp.entrySet())
            {
                Long id = mp.getKey();
                Integer stk = mp.getValue();
                Optional<Product> p1  = productRepo.findById(id);
                if(p1.isPresent()){
                    Product temp = p1.get();
                    temp.setStock(stk);
                    outProduct.add(productRepo.save(temp));
                }else{
                    return ResponseEntity.badRequest().body("Product not found with id : " + id);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(outProduct);

    }

    @GetMapping("/getPagedData")
    public ResponseEntity<Object> getPagedData(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){

        Pageable pageable = PageRequest.of(page, size,Sort.by("stock").descending());
        Page<Product> pg = productRepo.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pg);

    }




}
