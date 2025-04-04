package com.lab.lib.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	
	 @Serial
	 private static final long serialVersionUID = 1372210541405289235L;

	 public BadRequestException(String message) {
		 super(message);
	 }
}
