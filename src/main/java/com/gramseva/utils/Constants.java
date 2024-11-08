package com.gramseva.utils;

public class Constants {

	public  static Long TOKEN_EXP_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 10;
	public  static Long TEMP_TOKEN_EXP_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 10;

	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";

	public final static String BEARER ="Bearer ";

	public static final String ACCESS_TOKEN = "accessToken";
	public static final String AUTHORIZATION= "AUTHORIZATION";
	
	
	public static final String RESOURCE_ALREADY_EXIST = "Resource Already Exist";
	public static final String RESOURCE_NOT_FOUND = "Resource Not Found";
	public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
	public static final String ROLE_NOT_FOUND = "Role Not Found";
	
	
	public static final String EMAIL_ALREADY_IN_USE = "This email address is already registered. Please use a different email.";
	public static final String CONTACT_NUMBER_ALREADY_IN_USE = "This contact number is already associated with an account. Please enter a different number.";
	
	public static final String USER_NOT_FOUND = "User Not Found";
	public static final String INVALID_CREDENTIALS = "Invalid Credentials";
	public static final String USER_LOGIN_SUCCESS = "User Login Successfully";
	public static final String OTP_GENERATED_SUCCESS = "OTP Generated Successfully";
	public static final String OTP_EXPIRE = "OTP Expired";
	public static final String INVALID_OTP = "Invalid OTP";
	public static final String OTP_VERIFY_SUCCESS = "OTP Verify Successfully";
	public static final String CHANGE_PASSWORD_SUCCESS = "Password Change Successfully";
	public static final String PREVIOUS_PASSWORD_MATCH = "New Password Is Same As Previous one";

	public static final String STATUS_UPDATE_SUCCESS = "Status Update Successfully";

	public static final String PERMISSION_DENIED ="Permission Denied";
	public static final String ACCESS_DENIED ="Access Denied";
	
	public static final String PERMISSION_UPDATED_SUCCESS="Permissions Updated Successfully";
	public static final String SESSION_EXPIRED = "Session Expired";
	public static final String ACCOUNT_IN_ACTIVE = "Account is InActive";
	public static final String TOKEN_EXPIRED = "Token Expired";
	public static final String AUTH_TOKEN = "authToken";
	public static  final  String INCORRECT_PASSWORD ="Incorrect Password";
	public static  final  String INVALID_TOKEN ="Invalid Token";

}
