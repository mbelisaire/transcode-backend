/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.form;

import com.transcode.entity.User;
import com.transcode.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/**
 *
 * @author myriam
 */
public class LogInForm {
    
    private static final String USERNAME_FIELD  = "username";
    private static final String PASSWORD_FIELD  = "password";
    private static final String ENCRYPTION_ALGO = "SHA-256";
    
    private final Map<String, String> errors  = new HashMap<>();
    private final UserService userService;
    
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public LogInForm(UserService userService) {
        this.userService = userService;
    }
    
    public User LogInUser (HttpServletRequest request){
        String username = getFieldValue(request, USERNAME_FIELD);
        String password = getFieldValue(request, PASSWORD_FIELD);
        
        User user = new User();
        
        try {
            user = usernameValidation(username);
            passwordValidation(password, user);
        } catch (FormValidationException e) {
            errors.put(USERNAME_FIELD, e.getMessage());
        }
        
        return user;
    }
    
    private User usernameValidation (String username) throws FormValidationException{
        if (username != null && username.trim().length() != 0) {
	        if (userService.findUserByUsername(username) == null ) {
	        	throw new FormValidationException("Username or password invalid.");
	        }
	        else{
	        	return userService.findUserByUsername(username);
	        }
	    } else {
	        throw new FormValidationException("Please, enter a username.");
	    }
    }
    
    private void passwordValidation (String password, User user) throws FormValidationException {
        if (password != null && password.trim().length() != 0) {
	        if (!checkPassword(password, user)) {
	        	throw new FormValidationException("Username or password invalid.");
	        } 
	    } else {
	        throw new FormValidationException("Please, enter your password.");
	    }
    }
    
    private static String getFieldValue( HttpServletRequest request, String fieldName ) {
        String value = request.getParameter( fieldName );
        if ( value == null || value.trim().length() == 0 ) {
            return null;
        } else {
            return value.trim();
        }
    }
    
    private boolean checkPassword(String password, User user)
    {
        boolean passwordChecked;
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ENCRYPTION_ALGO );
        passwordEncryptor.setPlainDigest( false );
        passwordChecked = passwordEncryptor.checkPassword(password, user.getPassword());
        return passwordChecked;
    }
}
