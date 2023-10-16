package com.yunusbagriyanik.elasticsearch.repository;

import com.yunusbagriyanik.elasticsearch.entity.Catalog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CatalogRepository extends ElasticsearchRepository<Catalog, String> {
}
