package DTOS;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private double price;
    private String description;
    private Integer stock;
}
