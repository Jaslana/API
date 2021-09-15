package com.Api1.API1.Exception;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ToString
public class ErroBad {
    String defaultMessage;
    String objectName;
    String field;
    Object rejectedValue;
    String code;

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<GlobalExeption> handle(MethodArgumentNotValidException exception) {
        List<GlobalExeption> DTO = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            GlobalExeption erro = new GlobalExeption(e.getField(), mensagem);
            DTO.add(erro);
        });
        return DTO;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GlobalErro> handle(IllegalStateException exception) {
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getMessage());
        erroDefaultException.setCampo("CPF");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDefaultException);
    }
}
