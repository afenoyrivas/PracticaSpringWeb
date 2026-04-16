package org.eduardomango.practicaspringweb.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
}
