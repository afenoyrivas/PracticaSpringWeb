package org.eduardomango.practicaspringweb.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {
    private long id;
    private String username;
    private String email;
}
