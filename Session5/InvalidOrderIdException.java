package com.fastfood.exception;
public class InvalidOrderIdException extends Exception{
    public InvalidOrderIdException(String message){
        super(message);
    }
}