package com.yunusbagriyanik.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "customers")
@Data
@Builder
public class Customer {
    @Id
    private String id;
    @Field(name = "firstName", type = FieldType.Text)
    private String firstName;
    @Field(name = "lastName", type = FieldType.Text)
    private String lastName;
    @Field(name = "address", type = FieldType.Text)
    private String address;
    @Field(name = "createdDate", type = FieldType.Date, format = DateFormat.basic_date_time)
    private String createdDate;
}
