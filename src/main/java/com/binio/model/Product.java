package com.binio.model;

import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", updatable = false, nullable = false)
    private Long product_id;

    @Column(name = "product_sku")
    private String productSku;

    @Column(name = "product_price")
    private double product_price;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_created_date")
    private ZonedDateTime product_created_date;

    @Column(name = "product_deleted")
    private Boolean productDeleted;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;
}
