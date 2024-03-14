package com.exam.exception;

public class InternalServerErrorException extends ExceptionCustom {

    public InternalServerErrorException(Object errors) {
        super("UNEXPECTED ERROR OCCURRED", errors);
    }

}
