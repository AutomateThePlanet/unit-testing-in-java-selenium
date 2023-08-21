package models;

import lombok.Data;

@Data
public class ProductDetails {
    private String name;
    private Integer id;
    private String price;
    private String model;
    private String brand;
    private String weight;
}