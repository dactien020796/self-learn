package com.tino.selflearning.service;

import com.tino.selflearning.dto.ProductDto;
import com.tino.selflearning.mapper.ProductMapper;
import com.tino.selflearning.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public List<ProductDto> find() {
    return repository.findAll(mapper::mapToDto);
  }

  public ProductDto find(Long id) throws Exception {
    return repository.findById(id, mapper::mapToDto);
  }

  public ProductDto save(ProductDto product) {
    com.tino.selflearning.entity.Product entity = mapper.mapToEntity(product);
    return repository.save(entity, mapper::mapToDto);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
