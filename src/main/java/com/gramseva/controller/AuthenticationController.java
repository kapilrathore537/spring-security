package com.gramseva.controller;

import com.gramseva.payload.requests.*;
import com.gramseva.payload.responses.ApiResponse;
import com.gramseva.payload.responses.LoginResponse;
import com.gramseva.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/public/user/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(this.authenticationService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/public/user/login-verify-otp")
    public ResponseEntity<ApiResponse> loginVerifyOtp(@RequestBody @Valid VerifyOtpRequest otpRequest, @RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(this.authenticationService.loginVerifyOtp(otpRequest, headers), HttpStatus.OK);
    }

    @PostMapping("/public/user/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody @Valid SendOtpRequest otpRequest) {
        return new ResponseEntity<ApiResponse>(this.authenticationService.sendOtpRequest(otpRequest), HttpStatus.OK);
    }

    @PostMapping("/public/user/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody @Valid VerifyOtpRequest otpRequest, @RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(this.authenticationService.verifyOtp(otpRequest, headers), HttpStatus.OK);
    }

    @PostMapping("/public/user/new-password")
    public ResponseEntity<ApiResponse> newPassword(@RequestBody @Valid NewPasswordRequest newPasswordRequest, @RequestHeader HttpHeaders headers) {
        return new ResponseEntity<ApiResponse>(this.authenticationService.newPassword(newPasswordRequest, headers), HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, @RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(this.authenticationService.changePassword(changePasswordRequest, headers), HttpStatus.OK);
    }

}
