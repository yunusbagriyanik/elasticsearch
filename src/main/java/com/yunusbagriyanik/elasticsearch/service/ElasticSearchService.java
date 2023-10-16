package com.yunusbagriyanik.elasticsearch.service;

import com.yunusbagriyanik.elasticsearch.entity.Catalog;
import com.yunusbagriyanik.elasticsearch.entity.Customer;
import com.yunusbagriyanik.elasticsearch.entity.Product;
import com.yunusbagriyanik.elasticsearch.repository.CatalogRepository;
import com.yunusbagriyanik.elasticsearch.repository.CustomerRepository;
import com.yunusbagriyanik.elasticsearch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticSearchService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    public List<Customer> findCustomersByFirstName(String search) {
        return customerRepository.findCustomersByFirstName(search);
    }

    public List<Customer> findByFirstNameLikeOrLastNameLike(String name, String surname) {
        return customerRepository.findByFirstNameLikeOrLastNameLike(name, surname);
    }

    public List<Customer> findCustomersByPaginating(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Product> findProductsByPaginating(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Catalog> findCatalogsByPaginating(int page, int size) {
        return catalogRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
