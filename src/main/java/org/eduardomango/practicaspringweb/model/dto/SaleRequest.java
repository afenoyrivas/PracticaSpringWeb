package org.eduardomango.practicaspringweb.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleRequest {
    private Long productId;
    private Long clientId;
    private Long quantity;
}
