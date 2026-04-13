package org.eduardomango.practicaspringweb.model.services;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.eduardomango.practicaspringweb.model.dto.SaleRequest;
import org.eduardomango.practicaspringweb.model.dto.SaleResponse;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaleService {
    private final IRepository<SaleEntity> saleRepository;
    private final ProductService productService;
    private final UserService userService;

    ///MAPEO-------------------------------------------------------------------------------------------------

    public SaleEntity requestToEntity(SaleRequest saleRequest){
        ProductEntity p = productService.findById(saleRequest.getProductId());
        UserEntity u = userService.findById(saleRequest.getClientId());

        return SaleEntity.builder()
                .id(0L)
                .products(p)
                .client(u)
                .quantity(saleRequest.getQuantity())
                .saleDate(LocalDate.now())
                .build();
    }

    public SaleResponse entityToResponse(SaleEntity saleEntity){
        return SaleResponse.builder()
                .id(saleEntity.getId())
                .products(saleEntity.getProducts())
                .client(saleEntity.getClient())
                .quantity(saleEntity.getQuantity())
                .saleDate(saleEntity.getSaleDate())
                .build();
    }

    public SaleEntity responseToEntity(SaleResponse saleResponse){
        return SaleEntity.builder()
                .id(saleResponse.getId())
                .products(saleResponse.getProducts())
                .quantity(saleResponse.getQuantity())
                .client(saleResponse.getClient())
                .saleDate(saleResponse.getSaleDate())
                .build();
    }

    ///LEER----------------------------------------------------------------------------------------------------

    public List<SaleResponse> findAll(){
        return saleRepository.findAll().stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    public SaleResponse findById(long saleId){
        SaleEntity sale = saleRepository.findAll().stream()
                .filter(s -> s.getId()==saleId)
                .findFirst()
                .orElseThrow(SaleNotFoundException::new);
        return entityToResponse(sale);
    }

    ///GUARDAR-----------------------------------------------------------------------------------------------

    public SaleResponse save(SaleRequest saleRequest){
        SaleEntity newSale = requestToEntity(saleRequest);
        saleRepository.save(newSale);
        return entityToResponse(newSale);
    }

    ///ELIMINAR----------------------------------------------------------------------------------------------

    public void delete(long saleId){
        SaleResponse response = findById(saleId);
        SaleEntity sale = responseToEntity(response);
        saleRepository.delete(sale);
    }

    ///ACTUALIZAR--------------------------------------------------------------------------------------------

    public SaleResponse update(long saleId, SaleRequest saleRequest){
        SaleResponse response = findById(saleId);
        SaleEntity sale = responseToEntity(response);

        ProductEntity p = productService.findById(saleRequest.getProductId());
        UserEntity u = userService.findById(saleRequest.getClientId());

        sale.setProducts(p);
        sale.setClient(u);
        sale.setQuantity(saleRequest.getQuantity());

        saleRepository.update(sale);

        return entityToResponse(sale);
    }
}
