package com.binio.model;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductApi {

    Long product_id;
    String product_sku;
    double product_price;
    String product_name;
    ZonedDateTime product_created_date;
}
