package by.bsuir.constructioncompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message){super(message);}
    public ObjectNotFoundException(String message, Throwable cause){
        super(message, cause);

    }
}