package com.example.productservice.controller;


import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    /**
     * create product
     * @param productRequest
     */
    // http://localhost:8082/api/product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product productRequest) {
        productService.createProduct(productRequest);
    }

    /**
     * delete by id
     * @param ProductId
     * @return
     */
    @DeleteMapping("/{ProductId}")
    @CacheEvict(value = "product", key = "#ProductId")
    public String deleteProduct(@PathVariable String  ProductId) {
        productService.deleteProduct(ProductId);
        return "xoá thành công id" + ProductId;
    }

    /**
     * update product
     */
    @PostMapping("/update")
    public Product updateRole(@RequestBody Product p) {
        Product product = productService.updateProduct(p);
        return product;
    }

    /**
     * get by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Cacheable(value = "product", key = "#id")
    public Product getRoleById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return product;
    }

    /**
     * get all
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


//    // http://localhost:8082/api/product/isStock?name=iphone_13&name=iphone13_red
    @GetMapping("/isStock")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> isInStock(@RequestParam List<String> name) {
        return productService.isInStock(name);
    }
}
