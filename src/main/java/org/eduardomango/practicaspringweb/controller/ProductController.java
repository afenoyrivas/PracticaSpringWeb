package org.eduardomango.practicaspringweb.controller;


import org.apache.coyote.Response;
import org.eduardomango.practicaspringweb.model.dto.ProductResponseDto;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<ProductResponseDto>> getAll() {

        List<ProductResponseDto> prods= productService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(prods);
    }


    @GetMapping
    public ResponseEntity<ProductResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }


    @PostMapping
    public ResponseEntity<ProductEntity> post(@RequestBody ProductResponseDto productResponseDto) {
        ProductEntity productEntity = ProductEntity.builder()
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();
        productService.save(productEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(productEntity);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> put(@PathVariable Long id, @RequestBody ProductResponseDto productResponseDto){
        ProductEntity productEntity=ProductEntity.builder()
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();

        productService.update(productEntity);

        return ResponseEntity.status(HttpStatus.OK).body(productEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable long id){

        productService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
