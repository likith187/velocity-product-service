package com.example.product.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiValidationError {
	@Schema(example = "createJobRequest")
	private String object;
	@Schema(example = "questions[0].answerType")
	private String field;
	@Schema(example = "Radio Buatton")
	private Object rejectedValue;
	@Schema(example = "must be any of [List Menu, Radio Button, Text Box, Check Box]")
	private String message;

	public ApiValidationError(String object, String field, Object rejectedValue, String message) {
		this.object = object;
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}

	ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
