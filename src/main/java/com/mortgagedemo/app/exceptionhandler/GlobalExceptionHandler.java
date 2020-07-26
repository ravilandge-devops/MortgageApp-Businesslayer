package com.mortgagedemo.app.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mortgagedemo.app.model.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	// Handle Global Exception
	@ExceptionHandler(GlobleAPIException.class)
	public ResponseEntity<ErrorDetails> handleGlobleAPIException(GlobleAPIException exception, WebRequest request) {
		String[] statusCode = exception.getStatusCode().toString().split(" ");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), Integer.parseInt(statusCode[0]),statusCode[1], exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<ErrorDetails>(errorDetails, exception.getStatusCode());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobleException(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), 500,"Internal Server Error", exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle Specific Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), 404,"Not Found", exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
}
