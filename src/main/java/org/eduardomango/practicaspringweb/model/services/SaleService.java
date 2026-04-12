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

@Service
@AllArgsConstructor
public class SaleService {
    private final IRepository<SaleEntity> saleRepository;
    private final ProductService productService;
    private final UserService userService;

    public SaleResponse toSaleResponse(SaleEntity saleEntity){
        return SaleResponse.builder()
                .id(saleEntity.getId())
                .products(saleEntity.getProducts())
                .client(saleEntity.getClient())
                .quantity(saleEntity.getQuantity())
                .saleDate(saleEntity.getSaleDate())
                .build();
    }

    public List<SaleEntity> findAll(){
        return saleRepository.findAll();
    }

    public SaleEntity findById(long saleId){
        return saleRepository.findAll().stream()
                .filter(s -> s.getId()==saleId)
                .findFirst()
                .orElseThrow(SaleNotFoundException::new);
    }

    public SaleResponse save(SaleRequest saleRequest){
        ProductEntity p = productService.findById(saleRequest.getProductId());
        UserEntity u = userService.findById(saleRequest.getClientId());

        SaleEntity newSale = SaleEntity.builder()
                .id(0L)
                .products(p)
                .client(u)
                .quantity(saleRequest.getQuantity())
                .saleDate(LocalDate.now())
                .build();

        saleRepository.save(newSale);

        return toSaleResponse(newSale);
    }

    public SaleResponse delete(long saleId){
        SaleEntity sale = findById(saleId);
        saleRepository.delete(sale);
        return toSaleResponse(sale);
    }

    public SaleResponse update(long saleId, SaleRequest saleRequest){
        SaleEntity sale = findById(saleId);

        ProductEntity p = productService.findById(saleRequest.getProductId());
        UserEntity u = userService.findById(saleRequest.getClientId());

        sale.setProducts(p);
        sale.setClient(u);
        sale.setQuantity(saleRequest.getQuantity());

        saleRepository.update(sale);

        return toSaleResponse(sale);
    }
}
