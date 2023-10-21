package com.yunusbagriyanik.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunusbagriyanik.elasticsearch.entity.Catalog;
import com.yunusbagriyanik.elasticsearch.entity.Customer;
import com.yunusbagriyanik.elasticsearch.entity.Product;
import com.yunusbagriyanik.elasticsearch.repository.CatalogRepository;
import com.yunusbagriyanik.elasticsearch.repository.CustomerRepository;
import com.yunusbagriyanik.elasticsearch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticSearchService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient restHighLevelClient;

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

    /**
     * searches for documents in the "customers" index based on a specific parameter within the address field.
     *
     * @param param the parameter to be used for the search query.
     * @return list of search results for the address-based search.
     */
    public List<SearchHit<Customer>> searchCustByAddr(String param) {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("address", param))
                .build();

        return elasticsearchOperations.search(
                searchQuery,
                Customer.class,
                IndexCoordinates.of("customers")
        ).getSearchHits();
    }

    public List<Customer> saveCustomers(List<Customer> customers) {
        return customers.stream()
                .map(this::addCustomerToElasticsearch)
                .filter(Objects::nonNull)
                .toList();
    }

    public Customer addCustomerToElasticsearch(Customer customer) {
        try {
            IndexRequest request = new IndexRequest();
            request.id(customer.getId());
            request.source(mapper.writeValueAsString(customer), XContentType.JSON);
            request.index("customers");
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);

            return customer;
        } catch (IOException e) {
            log.error("Error: ", e);
        }

        return null;
    }
}
