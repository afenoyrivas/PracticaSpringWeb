package org.eduardomango.practicaspringweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter


public class ProductRequestDto {

    private String name;
    private double price;
    private String description;



}
