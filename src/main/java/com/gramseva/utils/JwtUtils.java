package com.gramseva.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${secret.key}")
	private String SECRET_KEY;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	  
    public Object getRole(String token) {
    	return extractAllClaims(token).get("role");
    }

	public Object getTokenType(String token){
		return extractAllClaims(token).get("type");
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("type",TokenType.ACCESS);
		return createToken(claims, userId);
	}

	public String generateTempToken(String userId,TokenType type) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("type",type);
		return createTempToken(claims, userId);
	}

	public String generateToken(String userId, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, userId);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(Constants.TOKEN_EXP_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, String username) {
		final String extractUsername = extractUsername(token);
		return (extractUsername.equals(username) && !isTokenExpired(token));
	}

	private String createTempToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(Constants.TEMP_TOKEN_EXP_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	// util method for authentication
	public Boolean isValidToken(String token,TokenType hasToBe){
      if(this.isTokenExpired(token))
		  return false;
	  TokenType type =TokenType.valueOf((String)this.getTokenType(token));
        return hasToBe.equals(type);
    }
}
