package com.tino.selflearning.controller;


import com.tino.selflearning.dto.ProductDto;
import com.tino.selflearning.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public List<ProductDto> find() {
    return productService.find();
  }

  @GetMapping("/{id}")
  public ProductDto find(@PathVariable(name = "id") Long id) {
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
