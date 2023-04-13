package br.com.madeira.api.controller;

import br.com.madeira.api.dto.ResponseDTO;
import br.com.madeira.api.exceptions.InvalidOperationException;
import br.com.madeira.api.exceptions.NoUserException;
import br.com.madeira.api.exceptions.PasswordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(NoUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleNoItemException(NoUserException ex){
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleInvalidOperationException(InvalidOperationException ex){
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDTO handleUsernameNotFoundException(UsernameNotFoundException ex){
        return new ResponseDTO(ex.getMessage());
    }

    @ExceptionHandler(PasswordNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDTO handlePasswordNotFoundException(PasswordNotFoundException ex){
        return new ResponseDTO(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hadleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> erros = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            erros.add(errorMessage);
        });

        return new ResponseDTO(erros);
    }
}