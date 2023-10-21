package com.yunusbagriyanik.elasticsearch.util;

public enum IndexEnum {
    CUSTOMER("customers"),
    PRODUCT("products"),
    CATALOG("catalogs");

    public final String indexName;

    IndexEnum(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexName() {
        return indexName;
    }
}
