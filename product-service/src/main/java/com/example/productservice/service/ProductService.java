package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.respository.ProductRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRespository productRepository;

    /**
     * save san pham
     * @param productRequest
     */
    public void createProduct(Product productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    /**
     * get san pham con so luong
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> isInStock(List<String> name) {
        return productRepository.findByNameIn(name).stream()
                .map(product ->
                        ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .isInStock(product.getQuantity() > 0)
                                .build()
                ).toList();
    }

    /**
     * get tat ca san pham
     * @return
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    /**
     * get san pham theo id
     * @param id
     * @return
     */
    public Product getProductById(String id) {
        Product product= productRepository.findById(id).get();
        return product;
    }

    /**
     * delete san pham theo id
     * @param roleId
     * @return
     */
    public String deleteProduct(String roleId) {
        productRepository.deleteById(roleId);
        return "xoa thanh cong";
    }
    /**
     * update san pham
     */
    public Product updateProduct(Product product) {
        Product product1= productRepository.save(product);
        return product1;
    }


    /**
     * get san pham theo ten
     * @param name
     * @return
     */
    public List<Product> getProductByName(List<String> name) {
        return productRepository.findByNameIn(name).stream()
                .map(product ->
                        mapToProductResponse(product)
                ).toList();
    }

    private Product mapToProductResponse(Product product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
