package com.yunusbagriyanik.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.lang.NonNull;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Bean
    @Override
    @NonNull
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(
                        ClientConfiguration.builder()
                                .connectedTo("localhost:9200")
                                .build()
                )
                .rest();
    }
}
