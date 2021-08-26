package com.Api1.API1.Exception;



import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import javax.validation.ConstraintViolationException;
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
        public List<GlobalErro> handle(MethodArgumentNotValidException exception){
            List<GlobalErro> DTO = new ArrayList<>();
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            fieldErrors.forEach(e ->{
                String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
                GlobalErro erro = new GlobalErro(e.getField(),mensagem);
                DTO.add(erro);
            });
            return DTO;
        }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public @ResponseBody Map<String, Object> handleConstraintViolation(ConstraintViolationException e, ServletWebRequest request) {
//        // emulate Spring DefaultErrorAttributes
//        final Map<String, Object> result = new LinkedHashMap<>();
//        result.put("timestamp", new Date());
//        result.put("path", request.getRequest().getRequestURI());
//        result.put("status", HttpStatus.BAD_REQUEST.value());
//        result.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
//        result.put("message", e.getMessage());
//        result.put("errors", e.getConstraintViolations().stream().map(cv -> SimpleObjectError.from(cv, messageSource, request.getLocale())));
//        return result;
//    }



}
