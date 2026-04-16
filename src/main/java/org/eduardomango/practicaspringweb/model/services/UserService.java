package org.eduardomango.practicaspringweb.model.services;


import org.eduardomango.practicaspringweb.model.dto.UserRequestDto;
import org.eduardomango.practicaspringweb.model.dto.UserResponseDto;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IRepository<UserEntity> userRepository;

    ///CONSTRUCTOR--------------------------------------------------------------------------------------------------

    public UserService(IRepository<UserEntity> userRepository) {
        this.userRepository = userRepository;
    }

    ///MAPEO-------------------------------------------------------------------------------------------------------

    public UserEntity requestToEntity(UserRequestDto userRequest){
        return UserEntity.builder()
                .id(0L)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public UserResponseDto entityToResponse(UserEntity userEntity){
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    ///BUSCAR------------------------------------------------------------------------------------------------------

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(long id) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByUsername(String username){
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByEmail(String email){
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    ///LEER--------------------------------------------------------------------------------------------------------

    public List<UserResponseDto> getAllDto(){
        List<UserEntity> users = findAll();
        return users.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDto getDtoById(long id){
        UserEntity entity = findById(id);
        return entityToResponse(entity);
    }

    public UserResponseDto getDtoByUsername(String username){
        UserEntity entity = findByUsername(username);
        return entityToResponse(entity);
    }

    public UserResponseDto getDtoByEmail(String email){
        UserEntity entity = findByEmail(email);
        return entityToResponse(entity);
    }

    ///GUARDAR-----------------------------------------------------------------------------------------------------

    public UserResponseDto save(UserRequestDto userRequest) {
        UserEntity newUser = requestToEntity(userRequest);
        userRepository.save(newUser);
        return entityToResponse(newUser);
    }

    ///ELIMINAR----------------------------------------------------------------------------------------------------

    public void delete(long userId) {
        UserEntity user = findById(userId);
        userRepository.delete(user);
    }

    ///ACTUALIZAR--------------------------------------------------------------------------------------------------

    public UserResponseDto update(long userId, UserRequestDto userRequest) {
        UserEntity user = findById(userId);

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        userRepository.update(user);

        return entityToResponse(user);
    }
}
