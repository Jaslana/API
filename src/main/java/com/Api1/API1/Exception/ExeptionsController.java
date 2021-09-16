package com.Api1.API1.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExeptionsController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ClienteNaoEncontradoCPF.class)
    public ResponseEntity<GlobalErro> handle(ClienteNaoEncontradoCPF exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getCause().getMessage());
        erroDefaultException.setCampo(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

    @ExceptionHandler(ContaNaoEncontradaNconta.class)
    public ResponseEntity<GlobalErro> handle(ContaNaoEncontradaNconta exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getCause().getMessage());
        erroDefaultException.setErro(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

    @ExceptionHandler(UsuarioJaCadastrado.class)
    public ResponseEntity<GlobalErro> handle(UsuarioJaCadastrado exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getCause().getMessage());
        erroDefaultException.setErro(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<GlobalErro> handle(MethodArgumentNotValidException exception) {
        List<GlobalErro> DTO = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            GlobalErro erro = new GlobalErro(e.getField(), mensagem);
            DTO.add(erro);
        });
        return DTO;
    }

    @ExceptionHandler(ContaJaCadastrada.class)
    public ResponseEntity<GlobalErro> handle(ContaJaCadastrada exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getCause().getMessage());
        erroDefaultException.setErro(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

}
