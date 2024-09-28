package com.example.petProject1.Service.Product;

import com.example.petProject1.Model.Product.product;
import com.example.petProject1.Repository.Product.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class productService {
    private productRepository operations;

    @Autowired
    public void setOperations(productRepository operations) {
        this.operations = operations;
    }
    public Page<product> getAllProducts(int page, int size) {
        return operations.findAll(PageRequest.of(page, size));
    }

    public product getConcreteProduct(int id){
        return operations.findById(id).orElse(null);
    }
    public void saveProduct(product product){
        operations.save(product);
    }
    public void updateProduct(int id,product product){
        product.setId(id);
        operations.save(product);
    }
    public void deleteProduct(int id){
        operations.deleteById(id);
    }
}
