package com.gramseva.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpRequest {
	@NotNull(message = "Email is required")
	@Email(message = "Email is invalid")
	private String email;
}
