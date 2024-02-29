package by.bsuir.constructioncompany.exceptions;

public class LackOfRightsException extends RuntimeException{
    public LackOfRightsException(String message){super(message);}
    public LackOfRightsException(String message, Throwable cause){
        super(message, cause);

    }
}