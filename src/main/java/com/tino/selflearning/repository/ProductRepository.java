package com.tino.selflearning.repository;

import com.tino.selflearning.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

}
