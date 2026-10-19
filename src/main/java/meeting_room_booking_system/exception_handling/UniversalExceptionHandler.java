package meeting_room_booking_system.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // register class as a global exception handler
public class UniversalExceptionHandler {

    @ExceptionHandler (BadRequest400Exception.class)
    public ProblemDetail badRequest (BadRequest400Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    } // end of resource not found handler

    @ExceptionHandler (Unauthorized401Exception.class)
    public ProblemDetail invalidCredentials (Unauthorized401Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    } // end of invalid credentials handler

    @ExceptionHandler (Conflict409Exception.class)
    public ProblemDetail conflict (Conflict409Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    } // end of conflict handler

    @ExceptionHandler (NotFound404Exception.class)
    public ProblemDetail notFound (NotFound404Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    } // end of not found handler

    // in case valid fails
    @ExceptionHandler (MethodArgumentNotValidException.class) // base method for validation build in spring
    public ProblemDetail validation (MethodArgumentNotValidException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid data");
    } // end of validation handler

    @ExceptionHandler(HttpMessageNotReadableException.class) // method for dealing with error in json formats
    public ProblemDetail invalidRequestBody(HttpMessageNotReadableException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request body. Check the JSON format and data types.");
    }
} // end of class body
