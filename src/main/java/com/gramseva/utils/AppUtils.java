package com.gramseva.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.gramseva.exception.UnauthorizedException;
import com.gramseva.model.DeactivateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Component
public class AppUtils {

    private static final Random rnd = new Random();

    private static final String BEARER_PREFIX = "Bearer ";

    public String getTokenFromHeader(String header) {
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            // Extract the token from the header (remove "Bearer " prefix)
            String token = header.substring(7);
            // Now, 'token' variable holds your Bearer token
            return token;
        }
        throw new UnauthorizedException("TOKEN IS REQUIRED");
    }

    // generate 4 digit OTP Number
    public String generateOtp() {
        int number = rnd.nextInt(1000, 9999);
        return String.format("%04d", number);
    }

    public String getTokenFromHeader(HttpHeaders headers){
       String header=headers.getFirst(HttpHeaders.AUTHORIZATION);
      return this.getTokenFromHeader(header);
    }




    public boolean isValid(LocalDateTime lastUpdatedDate, DeactivateStatus status) {
        long diffInDays = ChronoUnit.DAYS.between(lastUpdatedDate, LocalDateTime.now());
        switch (status) {
            case FOURTEEN_DAYS -> {
                if (diffInDays > 14)
                    return true;
            }
            case SEVEN_DAYS -> {
                if (diffInDays > 7)
                    return true;
            }
            default -> {
                return false;
            }
        }
        return  false;
    }
}
