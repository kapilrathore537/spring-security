package com.gramseva.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gramseva.exception.BadRequestException;
import com.gramseva.exception.InvalidOtpException;
import com.gramseva.exception.ResourceNotFoundException;
import com.gramseva.exception.UnauthorizedException;
import com.gramseva.model.DeactivateStatus;
import com.gramseva.model.User;
import com.gramseva.payload.requests.ChangePasswordRequest;
import com.gramseva.payload.requests.LoginRequest;
import com.gramseva.payload.requests.NewPasswordRequest;
import com.gramseva.payload.requests.SendOtpRequest;
import com.gramseva.payload.requests.VerifyOtpRequest;
import com.gramseva.payload.responses.ApiResponse;
import com.gramseva.repository.UserRepository;
import com.gramseva.service.IAuthenticationService;
import com.gramseva.utils.AppUtils;
import com.gramseva.utils.Constants;
import com.gramseva.utils.EmailUtils;
import com.gramseva.utils.JwtUtils;
import com.gramseva.utils.TokenType;

import jakarta.mail.MessagingException;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        // Look up user by email from login request
        User user = this.userRepository.findByEmail(loginRequest.getEmail().trim()).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

       System.out.println(( user.getDeactivateStatus() != null
               && this.isValid(user.getStatusUpdatedDate(), user.getDeactivateStatus())));
        // Check if user account is active or able to reactivate
        if (user.getIsActive() || user.getDeactivateStatus() != null
                && this.isValid(user.getStatusUpdatedDate(), user.getDeactivateStatus())) {

            // Validate password by comparing encoded values
            if (!this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new UnauthorizedException(Constants.INVALID_CREDENTIALS);
            }
            // Generate OTP and temp token for OTP verification
            String otp = this.appUtils.generateOtp();
            String authToken = this.jwtUtils.generateTempToken(user.getUserId(), TokenType.OTP_VERIFY);

            user.setIsActive(true);
            user.setDeactivateStatus(DeactivateStatus.IS_ACTIVE);
            user.setOtp(otp);
            user.setIsOtpExpired(false);
            this.userRepository.save(user); // Update user details with OTP and activation

            // Send OTP to user email
            try {
                this.emailUtils.sendEmail(user.getEmail(), otp);
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }

            Map<String, Object> response = new HashMap<>();
            response.put(Constants.AUTH_TOKEN, authToken);
            response.put("otp", otp);
            return new ApiResponse(Constants.OTP_GENERATED_SUCCESS, response, HttpStatus.OK.value());
        }
        throw new UnauthorizedException(Constants.ACCOUNT_IN_ACTIVE);
    }

    @Override
    public ApiResponse loginVerifyOtp(VerifyOtpRequest otpRequest, HttpHeaders headers) {
        // Extract token from headers and validate it for OTP verification
        String token = this.appUtils.getTokenFromHeader(headers);
        if (!this.jwtUtils.isValidToken(token, TokenType.OTP_VERIFY)) {
            throw new UnauthorizedException(Constants.INVALID_TOKEN);
        }
        User user = this.getUserFromToken(token);
        if (!user.getIsActive()) {
            throw new UnauthorizedException(Constants.ACCOUNT_IN_ACTIVE);
        }
        // Verify OTP and generate access token if valid
        if (user.getOtp().equals(otpRequest.getOtp()) && !user.getIsOtpExpired()) {
            String accessToken = this.jwtUtils.generateToken(user.getUserId());
            user.setIsOtpExpired(true); // Mark OTP as expired
            this.userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put(Constants.ACCESS_TOKEN, accessToken);
            return new ApiResponse(Constants.USER_LOGIN_SUCCESS, response, HttpStatus.OK.value());
        } else
            throw new InvalidOtpException(Constants.INVALID_OTP);
    }

    @Override
    public ApiResponse verifyOtp(VerifyOtpRequest otpRequest, HttpHeaders headers) {
        // Similar to loginVerifyOtp but generates temp_token for password change
        String token = this.appUtils.getTokenFromHeader(headers);
        if (!this.jwtUtils.isValidToken(token, TokenType.OTP_VERIFY)) {
            throw new UnauthorizedException(Constants.INVALID_TOKEN);
        }
        User user = this.getUserFromToken(token);
        if (!user.getIsActive()) {
            throw new UnauthorizedException(Constants.ACCOUNT_IN_ACTIVE);
        }
        if (user.getOtp().equals(otpRequest.getOtp()) && !user.getIsOtpExpired()) {
            String authToken = this.jwtUtils.generateTempToken(user.getUserId(), TokenType.NEW_PASSWORD);
            user.setIsOtpExpired(true);
            this.userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put(Constants.AUTH_TOKEN, authToken);
            return new ApiResponse(Constants.OTP_VERIFY_SUCCESS, response, HttpStatus.OK.value());
        } else
            throw new InvalidOtpException(Constants.INVALID_OTP);
    }

    @Override
    public ApiResponse newPassword(NewPasswordRequest newPasswordRequest, HttpHeaders headers) {
        // Set new password after validating new password token
        String token = this.appUtils.getTokenFromHeader(headers);
        if (!this.jwtUtils.isValidToken(token, TokenType.NEW_PASSWORD)) {
            throw new UnauthorizedException(Constants.INVALID_TOKEN);
        }
        User user = this.getUserFromToken(token);
        if (!user.getIsActive()) {
            throw new UnauthorizedException(Constants.ACCOUNT_IN_ACTIVE);
        }
        user.setPassword(this.passwordEncoder.encode(newPasswordRequest.getNewPassword().trim())); // Update password
        this.userRepository.save(user);
        return new ApiResponse(Constants.CHANGE_PASSWORD_SUCCESS, null, HttpStatus.OK.value());
    }

    @Override
    public ApiResponse sendOtpRequest(SendOtpRequest otpRequest) {
        // Send OTP to user email after checking if user is active
        User user = this.userRepository.findByEmail(otpRequest.getEmail().trim()).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

        if (user.getIsActive() || user.getDeactivateStatus() != null
                && this.isValid(user.getStatusUpdatedDate(), user.getDeactivateStatus())) {
            user.setIsActive(true);
            user.setDeactivateStatus(DeactivateStatus.IS_ACTIVE);
            String otp = this.appUtils.generateOtp();
            String authToken = this.jwtUtils.generateTempToken(user.getUserId(), TokenType.OTP_VERIFY);
            user.setOtp(otp);
            user.setIsOtpExpired(false);
            this.userRepository.save(user);
            // Send OTP to user email
            try {
                this.emailUtils.sendEmail(user.getEmail(), otp);
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }

            Map<String, Object> response = new HashMap<>();
            response.put(Constants.AUTH_TOKEN, authToken);
            response.put("otp", otp);
            return new ApiResponse(Constants.OTP_GENERATED_SUCCESS, response, HttpStatus.OK.value());
        }
        throw new UnauthorizedException(Constants.ACCOUNT_IN_ACTIVE);
    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest changePasswordRequest, HttpHeaders headers) {
        // Change password by validating the old password and encoding new one
        String token = this.appUtils.getTokenFromHeader(headers);
        User user = this.getUserFromToken(token);
        if (this.passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(changePasswordRequest.getNewPassword().trim()));
            this.userRepository.save(user);
            return new ApiResponse(Constants.CHANGE_PASSWORD_SUCCESS, HttpStatus.OK.value());
        }
        throw new BadRequestException(Constants.INCORRECT_PASSWORD); // Incorrect password exception
    }

    private User getUserFromToken(String token) {
        // Extract userId from token and retrieve user from repository
        String userId = this.jwtUtils.extractUsername(token);
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
    }

    private boolean isValid(LocalDateTime lastUpdatedDate, DeactivateStatus status) {
        // Check if deactivation period has expired based on status
        long diffInDays = ChronoUnit.DAYS.between(lastUpdatedDate, LocalDateTime.now());
        return switch (status) {
            case FOURTEEN_DAYS -> diffInDays > 14;
            case SEVEN_DAYS -> diffInDays > 7;
            case IS_ACTIVE -> true;
            default -> false;
        };
    }
}
