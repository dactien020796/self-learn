package com.tino.selflearning.controller;


import com.tino.selflearning.dto.ProductDto;
import com.tino.selflearning.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public List<ProductDto> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productService.find(pageable);
  }

  @GetMapping("/{id}")
  public ProductDto find(@PathVariable(name = "id") Long id) throws Exception {
    return productService.find(id);
  }

  @PostMapping
  public ProductDto add(@RequestBody ProductDto product) {
    return productService.save(product);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") Long id) {
    productService.delete(id);
  }
}
