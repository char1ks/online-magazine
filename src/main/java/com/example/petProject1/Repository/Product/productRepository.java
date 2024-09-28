package com.example.petProject1.Repository.Product;

import com.example.petProject1.Model.Product.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository  extends JpaRepository<product,Integer> {
    Page<product> findAll(Pageable pageable);
}
