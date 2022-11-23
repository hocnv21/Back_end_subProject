package com.example.productservice.respository;

import com.example.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRespository extends MongoRepository<Product,String> {

    List<Product> findByNameIn(List<String> name);
}
