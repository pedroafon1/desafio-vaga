package com.pedroafonso.first_spring_app.exceptions;

public class ArquivoInvalidoException extends RuntimeException {
    public ArquivoInvalidoException(String message) {
        super(message);
    }
}
