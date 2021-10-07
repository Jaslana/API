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

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExeptionsHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<StandardError> handle(UsuarioNaoEncontradoException exception){
        StandardError erros = new StandardError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<StandardError> handle(ContaNaoEncontradaException exception){
        StandardError erros = new StandardError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<StandardError> handle(UsuarioJaCadastradoException exception){
        StandardError erros = new StandardError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StandardErrorMetodo>> handle(MethodArgumentNotValidException exception) {
        List<StandardErrorMetodo> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            StandardErrorMetodo erro = new StandardErrorMetodo(e.getField(), mensagem);
            dto.add(erro);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(ContaJaCadastradaException.class)
    public ResponseEntity<StandardError> handle(ContaJaCadastradaException exception){
        StandardError standardError = new StandardError(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardError);
    }

}
