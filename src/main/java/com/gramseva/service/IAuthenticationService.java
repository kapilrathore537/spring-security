package com.gramseva.service;

import com.gramseva.payload.requests.*;
import com.gramseva.payload.responses.ApiResponse;
import com.gramseva.payload.responses.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IAuthenticationService {
    public ApiResponse login(LoginRequest loginRequest);

    public ApiResponse verifyOtp(VerifyOtpRequest otpRequest, HttpHeaders headers);

    public ApiResponse newPassword(NewPasswordRequest newPasswordRequest, HttpHeaders headers);

    public ApiResponse loginVerifyOtp(VerifyOtpRequest otpRequest, HttpHeaders headers);

    public ApiResponse sendOtpRequest(SendOtpRequest otpRequest);

    public ApiResponse changePassword(ChangePasswordRequest changePasswordRequest, HttpHeaders headers);
}
