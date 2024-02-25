package by.bsuir.constructioncompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CannotBeDeletedException extends RuntimeException{
    public CannotBeDeletedException(String message){super(message);}
    public CannotBeDeletedException(String message, Throwable cause){
        super(message, cause);
    }
}
