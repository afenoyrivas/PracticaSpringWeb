package org.eduardomango.practicaspringweb.model.mappers;

import org.eduardomango.practicaspringweb.model.dto.UserRequestDto;
import org.eduardomango.practicaspringweb.model.dto.UserResponseDto;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;

public class UserMapper {
    public static UserEntity requestToEntity(UserRequestDto userRequest){
        return UserEntity.builder()
                .id(0L)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public static UserResponseDto entityToResponse(UserEntity userEntity){
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }
}
