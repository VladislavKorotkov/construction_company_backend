package by.bsuir.constructioncompany.exceptions;

public class ImpossibleToBlockTheUserException extends RuntimeException{
    public ImpossibleToBlockTheUserException(String message){super(message);}
    public ImpossibleToBlockTheUserException(String message, Throwable cause){
        super(message, cause);

    }
}