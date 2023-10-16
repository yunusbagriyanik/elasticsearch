package com.yunusbagriyanik.elasticsearch.repository;

import com.yunusbagriyanik.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
