package br.com.madeira.api.services;

import br.com.madeira.api.dto.UserDTO;
import br.com.madeira.api.dto.UserResponseDTO;
import br.com.madeira.api.entity.User;
import br.com.madeira.api.exceptions.NoUserException;
import br.com.madeira.api.repositories.UserRepository;
import br.com.madeira.api.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream().map(user ->
                new UserResponseDTO(user.getId(), user.getName(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.getUsername(), userToSave.getPassword());
    }

    public UserDTO findById(long id){
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoUserException("Usuário não encontrado!");
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword());
    }

    public UserDTO update(UserDTO user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);
        Optional<User> userToEdit = userRepository.findById(userToSave.getId());

        if(!userToEdit.isPresent()){
            throw new NoUserException("Usuário não encontrado!");
        }

        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), user.getUsername(), user.getPassword());
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public User getByUserName(String username){
        return userRepository.findUserByUsername(username);
    }
}
