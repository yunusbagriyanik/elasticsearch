package com.yunusbagriyanik.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "products")
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private List<String> catalogs;
}
