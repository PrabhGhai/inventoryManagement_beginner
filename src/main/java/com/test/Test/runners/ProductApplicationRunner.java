package com.test.Test.runners;

import com.test.Test.models.Product;
import com.test.Test.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ProductApplicationRunner implements ApplicationRunner {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("----- Running ProductApplicationRunner -----");

        createProduct("Laptop", "High-performance laptop", 65000, 10);
        createProduct("Mobile Phone", "Latest Android smartphone", 25000, 20);
        createProduct("Rockerz 425", "Wireless noise cancelling headset", 5000, 30);
        createProduct("Musical headphone", "noises cancelling", 5000, 30);
        createProduct("Iphone 16 pro max", "a line of smartphones developed and marketed by Apple Inc. that run the iOS operating system", 168000, 40);
        System.out.println("----- ProductApplicationRunner Finished -----");
    }

    private void createProduct(String name, String description, double price, int stock) {


            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);

            productRepo.save(product);

            System.out.println("Created product: " + name);

    }
}
