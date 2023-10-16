package com.yunusbagriyanik.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "catalogs")
@Data
@Builder
public class Catalog {
    @Id
    private String id;
    private String name;
    private String description;
}
