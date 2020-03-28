package com.binio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.binio.model.Product;
import com.binio.model.ProductApi;
import com.binio.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(getProducts());
        List<ProductApi> products = productService.getAllProducts();
        assertEquals(2,products.size());
    }

    private List<Product> getProducts() {
        return Arrays.asList(
                getTestProduct("111111", 10.99, "Product A", 1L),
                getTestProduct("222222", 20.99, "Product B", 2L)
        );
    }

    private Product getTestProduct(final String s, final double v, final String s2, final long l) {
        return Product.builder()
                .productSku(s)
                .product_price(v)
                .product_name(s2)
                .product_id(l)
                .productDeleted(false)
                .product_created_date(ZonedDateTime.now())
                .build();
    }

    @Test
    void getProductById() {
        when(productRepository.findById(any())).thenReturn(Optional.of(getTestProduct("111111", 10.99, "Product A", 1L)));
        ProductApi productApi = productService.getProductById(1L).get();
        assertEquals("Product A", productApi.getProduct_name());
        assertEquals("111111", productApi.getProduct_sku());
        assertEquals(10.99, productApi.getProduct_price());
    }

    @Test
    void getProductBySku() {
        when(productRepository.findByProductSku(any())).thenReturn(Optional.of(getTestProduct("111111", 10.99, "Product A", 1L)));
        ProductApi productApi = productService.getProductBySku("111111").get();
        assertEquals("Product A", productApi.getProduct_name());
        assertEquals("111111", productApi.getProduct_sku());
        assertEquals(10.99, productApi.getProduct_price());
    }

    @Test
    void getProductBySkuAndProductDeleted() {
        when(productRepository.findByProductSkuAndProductDeleted(any(), any())).thenReturn(Optional.of(getTestProduct("111111", 10.99, "Product A", 1L)));
        ProductApi productApi = productService.getProductBySkuAndProductDeleted("111111", false).get();
        assertEquals("Product A", productApi.getProduct_name());
        assertEquals("111111", productApi.getProduct_sku());
        assertEquals(10.99, productApi.getProduct_price());
    }

    @Test
    void updateProduct() {
        when(productRepository.findById(any())).thenReturn(Optional.of(getTestProduct("111111", 10.99, "Product A", 1L)));
        when(productRepository.save(any())).thenReturn(getTestProduct("111111", 20.00, "Product A Updated", 1L));
        ProductApi productApi = productService.updateProduct(getUpdatedProductApi()).get();
        assertEquals("Product A Updated", productApi.getProduct_name());
        assertEquals("111111", productApi.getProduct_sku());
        assertEquals(20.00, productApi.getProduct_price());
    }

    private ProductApi getUpdatedProductApi() {
        return ProductApi.builder()
                .product_name("Product A Updated")
                .product_id(1L)
                .product_price(20.00)
                .product_sku("111111").build();
    }

    @Test
    void deleteProduct() {
        //@TODO
    }

    @Test
    void createProduct() throws ProductApiException {
        when(productRepository.save(any())).thenReturn(getTestProduct("111111", 20.00, "Product A Updated", 1L));
        ProductApi productApi = productService.createProduct(getUpdatedProductApi()).get();
        assertEquals("Product A Updated", productApi.getProduct_name());
        assertEquals("111111", productApi.getProduct_sku());
        assertEquals(20.00, productApi.getProduct_price());

    }

    @Test
    void createProduct_expectExceptionWhenProductIsNull() {
        Assertions.assertThrows(ProductApiException.class, () -> {
            productService.createProduct(null).get();
        });

    }
}