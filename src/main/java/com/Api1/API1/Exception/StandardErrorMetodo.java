package com.Api1.API1.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardErrorMetodo {
    private String campo;
    private String message;
}
