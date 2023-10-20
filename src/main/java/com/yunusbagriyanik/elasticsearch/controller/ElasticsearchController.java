package com.yunusbagriyanik.elasticsearch.controller;

import com.yunusbagriyanik.elasticsearch.entity.Customer;
import com.yunusbagriyanik.elasticsearch.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/elasticsearch")
@Slf4j
public class ElasticsearchController {
    private final ElasticSearchService elasticSearchService;

    @GetMapping("/get-customers-by-first-name")
    public ResponseEntity<List<Customer>> findCustomersByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(elasticSearchService.findCustomersByFirstName(firstName));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> findByFirstNameLikeOrLastNameLike(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(elasticSearchService.findByFirstNameLikeOrLastNameLike(firstName, lastName));
    }

    @GetMapping("/customers/page")
    public ResponseEntity<?> findCustomersByPaginating(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(elasticSearchService.findCustomersByPaginating(page, size));
    }

    @GetMapping("/products/page")
    public ResponseEntity<?> findProductsByPaginating(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(elasticSearchService.findProductsByPaginating(page, size));
    }

    @GetMapping("/catalogs/page")
    public ResponseEntity<?> findCatalogsByPaginating(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(elasticSearchService.findCatalogsByPaginating(page, size));
    }

    @GetMapping("/searchCustByAddr")
    public ResponseEntity<?> searchCustomerByAddr(@RequestParam String address) {
        return ResponseEntity.ok(elasticSearchService.searchCustByAddr(address));
    }
}
