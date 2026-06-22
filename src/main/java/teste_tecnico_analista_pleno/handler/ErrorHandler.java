package teste_tecnico_analista_pleno.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import teste_tecnico_analista_pleno.handler.exceptions.AccountNotFound;
import teste_tecnico_analista_pleno.handler.exceptions.FeeNotApplicableException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

@ExceptionHandler(FeeNotApplicableException.class)
    public ResponseEntity<ErrorResponse> handleFeeNotApplicable(
        FeeNotApplicableException ex) {

    return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(new ErrorResponse(
                    ex.getMessage(),
                    HttpStatus.UNPROCESSABLE_ENTITY.value()
            ));
            
}
@ExceptionHandler(AccountNotFound.class)
public ResponseEntity<ErrorResponse> handleAccountNotFound(
        AccountNotFound ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value()
            ));
}
}
