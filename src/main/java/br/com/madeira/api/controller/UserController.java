package br.com.madeira.api.controller;


import br.com.madeira.api.dto.ResponseDTO;
import br.com.madeira.api.dto.UserDTO;
import br.com.madeira.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody UserDTO user){
        try {
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody UserDTO user){
      try{
          return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
      } catch (Exception error){
          return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        try
        {
            userService.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Usuário removido com sucesso!"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException error){
            return new ResponseEntity<>(new ResponseDTO("Não foi possível localizar o usuário!"), HttpStatus.BAD_REQUEST);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}