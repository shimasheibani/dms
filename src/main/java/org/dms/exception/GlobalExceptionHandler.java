//package org.dms.exception;
//
//import org.dms.dto.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Response> handleAllExceptions(Exception e) {
//        Response response = Response.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(e.getMessage()).build();
//        return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Response> handleNotFoundException(Exception e) {
//        Response response = Response.builder().status(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
//        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler(NameValueRequiredException.class)
//    public ResponseEntity<Response> handleNameValueRequiredException(Exception e) {
//        Response response = Response.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
//        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(InvalidCredentialsException.class)
//    public ResponseEntity<Response>  handleInvalidCredentialException(InvalidCredentialsException ex){
//        Response response = Response.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//}
