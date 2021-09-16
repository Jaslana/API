package com.Api1.API1.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExeptionsController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalErro> handle(RuntimeException exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setCampo("CPF");
        erroDefaultException.setErro(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

    @ExceptionHandler(ExceptionDefault.class)
    public ResponseEntity<GlobalErro> handle(ExceptionDefault exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setErro(exception.getMessage());
        erroDefaultException.setCampo( exception.getCause().getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }

    @ExceptionHandler(RuntimeExceptionNconta.class)
    public ResponseEntity<GlobalErro> handle(RuntimeExceptionNconta exception){
        GlobalErro erroDefaultException = new GlobalErro();

        erroDefaultException.setCampo("Numero da Conta");
        erroDefaultException.setErro(exception.getMessage());

        return ResponseEntity.badRequest().body(erroDefaultException);
    }



//    @ExceptionHandler (HttpServerErrorException.InternalServerError.class)
//    public ResponseEntity<GlobalErro> handle (HttpServerErrorException.InternalServerError exception){
//        GlobalErro erroDefaultException = new GlobalErro();
//
////        erroDefaultException.setErro(exception.getMessage());
////        erroDefaultException.setCampo(messageSource.getMessage(exception, LocaleContextHolder.getLocale())));
//
//        return ResponseEntity.badRequest().body(erroDefaultException);
//    }

}
