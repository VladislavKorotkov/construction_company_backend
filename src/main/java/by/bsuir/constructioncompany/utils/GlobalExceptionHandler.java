package by.bsuir.constructioncompany.utils;


import by.bsuir.constructioncompany.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException ex, WebRequest req){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException){
        return new ResponseEntity<>(objectNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(Exception exception ){
        return new ResponseEntity<>("SQL error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return new ResponseEntity<>(userAlreadyExistsException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CannotBeDeletedException.class)
    public ResponseEntity<String> handleCannotBeDeletedException(CannotBeDeletedException cannotBeDeletedException){
        return new ResponseEntity<>(cannotBeDeletedException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<>(illegalArgumentException.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImpossibleToBlockTheUserException.class)
    public ResponseEntity<String> impossibleToBlockTheUserExceptionException(ImpossibleToBlockTheUserException impossibleToBlockTheUserException){
        return new ResponseEntity<>(impossibleToBlockTheUserException.getMessage(),  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LackOfRightsException.class)
    public ResponseEntity<String> handleLackOfRightsException(LackOfRightsException lackOfRightsException){
        return new ResponseEntity<>(lackOfRightsException.getMessage(),  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EstimateEmptyException.class)
    public ResponseEntity<String> handleEstimateEmptyException(EstimateEmptyException estimateEmptyException){
        return new ResponseEntity<>(estimateEmptyException.getMessage(),  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContractHasBeenNotCreatedException.class)
    public ResponseEntity<String> handleContractHasBeenNotCreatedException(ContractHasBeenNotCreatedException contractHasBeenNotCreatedException){
        return new ResponseEntity<>(contractHasBeenNotCreatedException.getMessage(),  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<String> handleIncorrectDataException(IncorrectDataException incorrectDataException){
        return new ResponseEntity<>(incorrectDataException.getMessage(),  HttpStatus.CONFLICT);
    }
}