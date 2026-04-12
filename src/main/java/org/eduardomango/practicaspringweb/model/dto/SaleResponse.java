package org.eduardomango.practicaspringweb.model.dto;

import lombok.*;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleResponse {
    private Long id;
    private ProductEntity products;
    private Long quantity;
    private UserEntity client;
    private LocalDate saleDate;
}
