package meeting_room_booking_system.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // register class as a global exception handler
public class UniversalExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    public ProblemDetail resourceNotFound (ResourceNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    } // end of resource not found handler

    @ExceptionHandler (InvalidRequisitionException.class)
    public ProblemDetail invalidRequisition (InvalidRequisitionException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    } // end of invalid requisition handler

    @ExceptionHandler (InvalidCredentialsException.class)
    public ProblemDetail invalidCredentials (InvalidCredentialsException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    } // end of invalid credentials handler

    @ExceptionHandler (ConflictException.class)
    public ProblemDetail conflict (ConflictException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    } // end of conflict handler

    // in case valid fails
    @ExceptionHandler (MethodArgumentNotValidException.class) // base method for validation build in spring
    public ProblemDetail validation (MethodArgumentNotValidException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid data");
    } // end of validation handler
} // end of class body
