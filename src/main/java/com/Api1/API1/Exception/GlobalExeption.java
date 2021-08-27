package com.Api1.API1.Exception;


import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
@ToString
public class GlobalExeption {
    String defaultMessage;
    String objectName;
    String field;
    Object rejectedValue;
    String code;

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
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
}
