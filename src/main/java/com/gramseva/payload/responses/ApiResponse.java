package com.gramseva.payload.responses;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

	public ApiResponse(String message,int status){
		this.message=message;
		this.status=status;
	}

	private String message;
	private Map<String, Object> body;
	private int status;
}
