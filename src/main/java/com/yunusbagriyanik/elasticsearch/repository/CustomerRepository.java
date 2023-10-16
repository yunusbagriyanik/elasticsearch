package com.yunusbagriyanik.elasticsearch.repository;

import com.yunusbagriyanik.elasticsearch.entity.Customer;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"firstName\": \"?0\"}}]}}")
    List<Customer> findCustomersByFirstName(String firstName);

    List<Customer> findByFirstNameLikeOrLastNameLike(String firstName, String lastName);
}
