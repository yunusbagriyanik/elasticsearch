package com.yunusbagriyanik.elasticsearch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunusbagriyanik.elasticsearch.entity.Catalog;
import com.yunusbagriyanik.elasticsearch.entity.Customer;
import com.yunusbagriyanik.elasticsearch.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class MockDataGenerator {
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper mapper = new ObjectMapper();

    //@PostConstruct
    public void init() {
        IntStream.range(0, 50)
                .parallel()
                .forEach(i -> addCatalogToElasticsearch((i + 1)));

        IntStream.range(0, 300000)
                .parallel()
                .forEach(i -> addCustomerToElasticsearch());

        IntStream.range(0, 1000000)
                .parallel()
                .forEach(i -> addProductToElasticsearch());
    }

    public void addCatalogToElasticsearch(int i) {
        try {
            IndexRequest request = new IndexRequest();
            Catalog catalog = Catalog.builder()
                    .id(String.valueOf(i))
                    .name(generateRandomFourLetterWord())
                    .description(UUID.randomUUID().toString())
                    .build();
            request.source(mapper.writeValueAsString(catalog), XContentType.JSON);
            request.index(IndexEnum.CATALOG.getIndexName());
            request.id(catalog.getId());
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    public void addProductToElasticsearch() {
        try {
            IndexRequest request = new IndexRequest();
            Product product = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(UUID.randomUUID().toString())
                    .description(UUID.randomUUID().toString())
                    .price(Double.parseDouble(generateRandomNumbersInRange(500, 5501, 1).get(0)))
                    .stock(Integer.parseInt(generateRandomNumbersInRange(100, 901, 1).get(0)))
                    .catalogs(generateRandomNumbersInRange(1, 51))
                    .build();
            request.source(mapper.writeValueAsString(product), XContentType.JSON);
            request.index(IndexEnum.PRODUCT.getIndexName());
            request.id(product.getId());
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    public void addCustomerToElasticsearch() {
        try {
            IndexRequest request = new IndexRequest();
            Customer customer = Customer.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName(UUID.randomUUID().toString())
                    .lastName(UUID.randomUUID().toString())
                    .address(UUID.randomUUID().toString())
                    .createdDate(DateUtil.currentDate())
                    .build();
            request.id(customer.getId());
            request.source(mapper.writeValueAsString(customer), XContentType.JSON);
            request.index(IndexEnum.CUSTOMER.getIndexName());
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("IndexResponse: {}", indexResponse);
        } catch (IOException e) {
            log.error("Error: ", e);
        }
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
