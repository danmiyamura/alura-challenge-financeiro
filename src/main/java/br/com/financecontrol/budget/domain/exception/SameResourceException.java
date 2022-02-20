package br.com.financecontrol.budget.domain.exception;

public class SameResourceException extends RuntimeException{
    public SameResourceException(String message){
        super(message);
    }
}
