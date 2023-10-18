package com.yunusbagriyanik.elasticsearch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunusbagriyanik.elasticsearch.entity.Catalog;
import com.yunusbagriyanik.elasticsearch.entity.Customer;
import com.yunusbagriyanik.elasticsearch.entity.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class MockDataGenerator {
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        IntStream.range(0, 50)
                .parallel()
                .forEach(i -> {
                            IndexRequest request = new IndexRequest();
                            addCatalogToElasticsearch(request, (i + 1));
                        }
                );
        IntStream.range(0, 300000)
                .parallel()
                .forEach(i -> {
                            IndexRequest request = new IndexRequest();
                            addCustomerToElasticsearch(request);
                        }
                );
        IntStream.range(0, 1000000)
                .parallel()
                .forEach(i -> {
                            IndexRequest request = new IndexRequest();
                            addProductToElasticsearch(request);
                        }
                );
    }

    public void addCatalogToElasticsearch(IndexRequest request, int i) {
        try {
            Catalog catalog = Catalog.builder()
                    .id(String.valueOf(i))
                    .name(generateRandomFourLetterWord())
                    .description(UUID.randomUUID().toString())
                    .build();
            request.source(mapper.writeValueAsString(catalog), XContentType.JSON);
            request.index("catalogs");
            request.id(catalog.getId());
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    public void addProductToElasticsearch(IndexRequest request) {
        try {
            Product product = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(UUID.randomUUID().toString())
                    .description(UUID.randomUUID().toString())
                    .price(Double.parseDouble(generateRandomNumbersInRange(500, 5501, 1).get(0)))
                    .stock(Integer.parseInt(generateRandomNumbersInRange(100, 901, 1).get(0)))
                    .catalogs(generateRandomNumbersInRange(1, 51))
                    .build();
            request.source(mapper.writeValueAsString(product), XContentType.JSON);
            request.index("products");
            request.id(product.getId());
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    public void addCustomerToElasticsearch(IndexRequest request) {
        try {
            Customer customer = Customer.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName(UUID.randomUUID().toString())
                    .lastName(UUID.randomUUID().toString())
                    .address(UUID.randomUUID().toString())
                    .createdDate(currentDate())
                    .build();
            request.id(customer.getId());
            request.source(mapper.writeValueAsString(customer), XContentType.JSON);
            request.index("customers");
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (IOException e) {
            log.error("Error: ", e);
        }
    }

    public String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

    public List<String> generateRandomNumbersInRange(int min, int max, int count) {
        return new Random().ints(min, max)
                .distinct()
                .limit(count)
                .mapToObj(Integer::toString)
                .toList();
    }

    public List<String> generateRandomNumbersInRange(int min, int max) {
        return new Random().ints(min, max)
                .distinct()
                .limit((new Random().nextInt(3) + 1))
                .mapToObj(Integer::toString)
                .toList();
    }

    public String generateRandomFourLetterWord() {
        Random rand = new Random();
        StringBuilder randomWord = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int randomIndex = rand.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            randomWord.append(randomChar);
        }

        return randomWord.toString();
    }
}
