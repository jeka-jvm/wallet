package org.example.wallet.config;

import jakarta.validation.ConstraintViolationException;
import org.example.wallet.exception.ErrorResponse;
import org.example.wallet.exception.InsufficientFundsException;
import org.example.wallet.exception.InvalidOperationTypeException;
import org.example.wallet.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String description = ex.getBindingResult().getFieldErrors().stream()
                                                                   .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                                   .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse(
                "VALIDATION_ERROR",
                description,
                "Убедитесь, что все поля заполнены правильно, и повторите попытку"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String description = ex.getConstraintViolations().stream()
                                                         .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                                         .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse(
                "CONSTRAINT_VIOLATION",
                description,
                "Проверьте нарушенные ограничения и повторите попытку"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "WALLET_NOT_FOUND",
                ex.getMessage(),
                "Убедитесь, что идентификатор кошелька верен, и повторите попытку"
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        ErrorResponse response = new ErrorResponse(
                "INSUFFICIENT_FUNDS",
                ex.getMessage(),
                "Прежде чем пытаться вывести средства, убедитесь, что на кошельке достаточно средств"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @ExceptionHandler(InvalidOperationTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationTypeException(InvalidOperationTypeException ex) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_OPERATION_TYPE",
                ex.getMessage(),
                "Убедитесь, что тип операции указан правильно (WITHDRAW или DEPOSIT)"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_REQUEST",
                "Некорректные данные в запросе: " + ex.getMessage(),
                "Убедитесь, что данные запроса соответствуют ожидаемому формату"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

}
