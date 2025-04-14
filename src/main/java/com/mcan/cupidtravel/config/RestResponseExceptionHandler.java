package com.mcan.cupidtravel.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LogManager.getLogger(RestResponseExceptionHandler.class);

	@ExceptionHandler(value = RuntimeException.class)
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
