package com.example.product.exception;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.product.models.ApiError;
import com.example.product.models.ApiValidationError;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
		List<ApiValidationError> list = new LinkedList<>();
		String ise = null;
		for (ObjectError oe : ex.getBindingResult().getAllErrors()) {
			if (oe instanceof FieldError fieldError) {
				list.add(new ApiValidationError(fieldError.getObjectName(), fieldError.getField(),
						fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
			} else {
				ise = oe.getDefaultMessage();
			}
		}
		if (ise != null) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, request, ise);
			apiError.setValidationErrors(list);
			return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, "validation errors");
		apiError.setValidationErrors(list);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, "validation errors");
		List<ApiValidationError> validationErrors = ex.getConstraintViolations().stream().map(
				e -> new ApiValidationError(null, e.getPropertyPath().toString(), e.getInvalidValue(), e.getMessage()))
				.toList();
		apiError.setValidationErrors(validationErrors);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) throws Throwable {
		ex.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, request, "something went wrong");
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<ApiError> handleOptimisticLockingFailureException(OptimisticLockingFailureException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT, request, ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ApiError> handleDataNotFoundException(DataNotFoundException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, request, ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ApiError> handleForbiddenException(ForbiddenException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, request, ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ApiError> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiError> handleMultipartException(MultipartException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ApiError> handleConversionFailedException(ConversionFailedException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, "invalid inputs");
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiError> handleConversionFailedException(MissingServletRequestParameterException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ApiError> handleConversionFailedException(MissingPathVariableException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleConversionFailedException(HttpMessageNotReadableException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, "invalid payload");
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ApiError> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, request, ex);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String propertyName = ex.getName();
		Object propertyValue = ex.getValue();
		String msg;
		if (propertyValue != null) {
			msg = MessageFormat.format("{0} is invalid for value {1}", propertyName, propertyValue);
		} else {
			msg = MessageFormat.format("{0} is invalid", propertyName);
		}
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request, msg);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
