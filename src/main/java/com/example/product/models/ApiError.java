package com.example.product.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiError {
	@Schema(example = "BAD_REQUEST")
	private HttpStatus status;
	@Schema(example = "29-05-2023 10:06:12")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	@Schema(example = "validation errors")
	private String message;
	@Schema(example = "uri=/amplify/v2/jobs")
	private String details;
	private List<ApiValidationError> validationErrors = new LinkedList<>();

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this.status = status;
	}

	public ApiError(HttpStatus status, String details, Throwable ex) {
		this();
		this.status = status;
		this.details = details;
		this.message = ex.getMessage();
	}

	public ApiError(HttpStatus status, String details, String message) {
		this();
		this.status = status;
		this.details = details;
		this.message = message;
	}

	public ApiError(HttpStatus status, WebRequest request, Throwable ex) {
		this(status, request.getDescription(false), ex);
	}
	
	public ApiError(HttpStatus status, WebRequest request, String message) {
		this(status, request.getDescription(false), message);
	}
}