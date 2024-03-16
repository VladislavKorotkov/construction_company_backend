package by.bsuir.constructioncompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EstimateEmptyException extends RuntimeException {
    public EstimateEmptyException(String message){super(message);}
}
