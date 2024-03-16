package by.bsuir.constructioncompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ContractHasBeenNotCreatedException extends RuntimeException{
    public ContractHasBeenNotCreatedException(String message){super(message);}
}
