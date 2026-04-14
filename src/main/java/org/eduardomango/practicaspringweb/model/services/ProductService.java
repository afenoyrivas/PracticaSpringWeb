package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.dto.ProductRequestDto;
import org.eduardomango.practicaspringweb.model.dto.ProductResponseDto;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.eduardomango.practicaspringweb.model.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final IRepository<ProductEntity> productRepository;

    public ProductService(IRepository<ProductEntity> productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    /// //////////////////////////////MAPEO/////////////////////////////////////////////////////
    public ProductEntity requestToEntity(ProductRequestDto productResponseDto){

        return ProductEntity.builder()
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();
    }

    public ProductResponseDto entityToResponse(ProductEntity productEntity){

        return  ProductResponseDto.builder()
                .id(productEntity.getId())
                .price(productEntity.getPrice())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .build();
    }

    public ProductEntity responseToEntity(ProductResponseDto productResponseDto){
        return ProductEntity.builder()
                .id(productResponseDto.getId())
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////
    public ProductResponseDto findById(long id) {
        ProductEntity product = productRepository.findAll()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
        return entityToResponse(product);
    }

    public List<ProductResponseDto> findByName(String name){
        List<ProductResponseDto> products = productRepository.findAll()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .map(this::entityToResponse)
                .collect(Collectors.toList());

        if(products.isEmpty())
        {
            throw new ProductNotFoundException();
        }

        return products;
    }


    public List<ProductResponseDto> findMoreExpensiveThan(Double price){
        List<ProductResponseDto> products= productRepository.findAll()
                .stream()
                .filter(user -> user.getPrice() > price)
                .map(this::entityToResponse)
                .collect(Collectors.toList());

        return products;
    }

    public void save(ProductEntity p) {
        productRepository.save(p);
    }

    public void delete(long id) {
        ProductResponseDto responseDto= findById(id);

        ProductEntity entity= responseToEntity(responseDto);

        productRepository.delete(entity);
    }

    public void update(ProductEntity p) {
        productRepository.update(p);
    }
}