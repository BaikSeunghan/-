package aswemake.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, ResponseStatusException.class, NullPointerException.class}) // 여러개의 Exception 추가할 수 있다.
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {

        RestApiException restApiException = new RestApiException();
        restApiException.setResponse(false);
        restApiException.setMessage(ex.getMessage());

        return new ResponseEntity(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
}
