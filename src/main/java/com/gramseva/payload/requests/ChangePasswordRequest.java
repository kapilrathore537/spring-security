package com.gramseva.payload.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotNull(message = "Old password is required")
    @NotEmpty(message ="Old password is required")
    private String oldPassword;
    @NotNull(message = "New password is required")
    @NotEmpty(message ="New password is required")
    private String newPassword;
}
