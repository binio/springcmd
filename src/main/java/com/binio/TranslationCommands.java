package com.binio;

import java.util.Locale;
import java.util.Optional;

import com.binio.model.Product;
import com.binio.model.ProductApi;
import com.binio.service.ProductApiException;
import com.binio.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent(value = " hello hello bum bum")
@AllArgsConstructor
public class TranslationCommands {

    private final TranslationService service;

    private final ProductService productService;

    @ShellMethod("Translate text from one language to another.")
    public String translate(
            @ShellOption(defaultValue = "hello") String text,
            @ShellOption(defaultValue = "en_US") Locale from,
            @ShellOption(defaultValue = "en_US") Locale to
    ) {
        // invoke service
        return service.translate(text, from, to);
    }

    @ShellMethod("Add product to DB.")
    public Optional<ProductApi> addProduct(
            @ShellOption(defaultValue = "0") String id,
            @ShellOption(defaultValue = "") String name,
            @ShellOption(defaultValue = "") String price
    ) {
        // invoke service
        ProductApi p = ProductApi.builder()
                .product_id(Long.valueOf(id))
                .product_name(name)
                .product_price(Double.valueOf(price))
                .build();

        try {
            return productService.createProduct(p);
        } catch (ProductApiException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
