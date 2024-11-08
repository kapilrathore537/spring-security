package com.gramseva.exception.handler;

import com.gramseva.exception.*;
import com.gramseva.payload.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage exceptions and provide appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceAlreadyExistException and returns a 406 Not Acceptable response.
     *
     * @param ex the ResourceAlreadyExistException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExistException(ResourceAlreadyExistException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE, ex.getMessage()),
                HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handles ResourceNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the ResourceNotFoundException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UnauthorizationException and returns a 401 Unauthorized response.
     *
     * @param ex the UnauthorizationException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizationException(UnauthorizedException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponse> badRequestException(InvalidOtpException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response with validation errors.
     *
     * @param ex the MethodArgumentNotValidException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorRes = new ErrorResponse();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorRes.setMessage(error.getDefaultMessage());
            errorRes.setError(HttpStatus.BAD_REQUEST);
            errorRes.setStatus(HttpStatus.BAD_REQUEST.value());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException ex){
    	return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
