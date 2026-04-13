package org.eduardomango.practicaspringweb.model.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ProductEntity {

    @EqualsAndHashCode.Include
    private long id;

    private String name;
    private double price;
    private String description;
}
