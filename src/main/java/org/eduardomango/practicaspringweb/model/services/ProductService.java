package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.dto.ProductRequestDto;
import org.eduardomango.practicaspringweb.model.dto.ProductResponseDto;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final IRepository<ProductEntity> productRepository;
///  //////////////////////Constructor///////////////////////////////

    public ProductService(IRepository<ProductEntity> productRepository) {
        this.productRepository = productRepository;
    }

/// ////////////BUSCAR////////////////////////////
    public List<ProductEntity>findAll(){
        return productRepository.findAll();
    }
    /// ////////////LEER////////////////////////////
    public List<ProductResponseDto>getAllDtos(){
        return findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    public ProductResponseDto getDtoById(long id){
    ProductEntity entity=findById(id);
    return entityToResponse(entity);
    }

    public List<ProductResponseDto>getDtoByName(String name){
    return findByName(name).stream()
            .map(this::entityToResponse)
            .toList();
    }

    public List<ProductResponseDto>getDtoMoreExpensiveThan(double price ){
    return findMoreExpensiveThan(price).stream()
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

    /// /////////////////////////////////METODOS DE CLASE////////////////////////////////////////////////////////////


    public ProductEntity findById(long id) {
        return  productRepository.findAll()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductEntity> findByName(String name){
        List<ProductEntity> products= productRepository.findAll()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if(products.isEmpty())
        {
            throw new ProductNotFoundException();
        }
        return products;
    }


    public List<ProductEntity> findMoreExpensiveThan(Double price){
          return productRepository.findAll()
                .stream()
                .filter(user -> user.getPrice() > price)
                .collect(Collectors.toList());


    }

    public void save(ProductEntity p) {
        productRepository.save(p);
    }

    public void delete(long id) {

        ProductEntity entity=findById(id);

        productRepository.delete(entity);
    }

    public void update(ProductEntity p) {
        productRepository.update(p);
    }
}