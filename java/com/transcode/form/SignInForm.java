/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.form;

import com.transcode.entity.User;
import com.transcode.service.UserService;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/**
 *
 * @author myriam
 */
public class SignInForm {
    
    private static final String USERNAME_FIELD      = "username";
    private static final String FIRSTNAME_FIELD     = "firstName";
    private static final String LASTNAME_FIELD      = "lastName";
    private static final String EMAIL_FIELD         = "email";
    private static final String BIRTHDATE_FIELD       = "birthDate";
    private static final String PASSWORD_FIELD      = "password";
    private static final String C_PASSWORD_FIELD    = "Cpassword";
    private static final String ATT_SESSION_USER    = "sessionUser";
    
    private static final String ENCRYPTION_ALGO     = "SHA-256";
    
    private final Map<String, String> errors          = new HashMap<>();
    private final UserService userService;
    
    
    public SignInForm (UserService userService){
        this.userService = userService;
    }
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public User addUser(HttpServletRequest request) throws UnsupportedEncodingException
    {
        String username     = getFieldValue(request, USERNAME_FIELD);
        String firtname     = getFieldValue(request, FIRSTNAME_FIELD);
        String lastname     = getFieldValue(request, LASTNAME_FIELD);
        String email        = getFieldValue(request, EMAIL_FIELD);
        String birthDate      = getFieldValue(request, BIRTHDATE_FIELD);
        String password     = getFieldValue(request, PASSWORD_FIELD);
        String cpassword    = getFieldValue(request, C_PASSWORD_FIELD);
        
        User user = new User();
        
            handleUsername(username, user);
            handleFirstname(firtname, user);
            handleLastname(lastname, user);
            handleEmail(email, user);
            handleBirthDate(birthDate, user);
            handlePassword(password, cpassword, user);
        
        return user;
    }
    
    public User UpdateUser(HttpServletRequest request) throws UnsupportedEncodingException
    {
        String username     = getFieldValue(request, USERNAME_FIELD);
        String firtname     = getFieldValue(request, FIRSTNAME_FIELD);
        String lastname     = getFieldValue(request, LASTNAME_FIELD);
        String email        = getFieldValue(request, EMAIL_FIELD);
        String birthDate      = getFieldValue(request, BIRTHDATE_FIELD);
        String password     = getFieldValue(request, PASSWORD_FIELD);
        String cpassword    = getFieldValue(request, C_PASSWORD_FIELD);
        
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
            handleFirstname(firtname, user);
            handleLastname(lastname, user);
            handleBirthDate(birthDate, user);
        
        return user;
        
    }
    
    private void handleUsername(String username, User user)
    {
        try {
            usernameValidation(username);
            user.setUsername(username);
        } catch (FormValidationException e){
            errors.put(USERNAME_FIELD, e.getMessage());
        }
    }
    
    private void handleFirstname(String firstname, User user)
    {
        try {
            firstnameValidation(firstname);
            user.setFirstName(firstname);
        } catch (FormValidationException e){
            errors.put(FIRSTNAME_FIELD, e.getMessage());
        }
    }
    
    private void handleLastname(String lastname, User user)
    {
        try {
            lastnameValidation(lastname);
            user.setLastName(lastname);
        } catch (FormValidationException e){
            errors.put(LASTNAME_FIELD, e.getMessage());
        }
    }
    
    private void handleEmail(String email, User user)
    {
        try {
            emailValidation(email);
            user.setEmail(email);
        } catch (FormValidationException e) {
            errors.put(EMAIL_FIELD, e.getMessage());
        }
    }
    
    private void handleBirthDate(String birthDate, User user)
    {
        try {
            Date date = birthDateValidation(birthDate);
            user.setBirthDate(date);
        } catch (FormValidationException e){
            errors.put(BIRTHDATE_FIELD, e.getMessage());
        }
    }
    
    private void handlePassword(String password, String cpassword, User user)
    {
        try{
            passwordValidation(password, cpassword);
            
            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm( ENCRYPTION_ALGO );
            passwordEncryptor.setPlainDigest( false );
            String encryptedPassword = passwordEncryptor.encryptPassword( password );

            user.setPassword( encryptedPassword );
        } catch (FormValidationException e) {
            errors.put(PASSWORD_FIELD, e.getMessage());
            errors.put(C_PASSWORD_FIELD, null);
        }
    }
    
    private void usernameValidation(String username)throws FormValidationException {
        if(username != null && username.trim().length() != 0)
        {
            if(userService.findUserByUsername(username) != null)
            {
                throw new FormValidationException("Already used.");
            }
        }
        else
        {
            throw new FormValidationException("Please, enter a user name.");
        }
    }
    
    private void firstnameValidation(String firstname) throws FormValidationException {
        if ( firstname == null || firstname.trim().length() == 0 ) 
        {
            throw new FormValidationException( "Please, enter your first name." );
        }
    }
    
    private void lastnameValidation(String lastname) throws FormValidationException {
        if ( lastname == null || lastname.trim().length() == 0 ) 
        {
            throw new FormValidationException( "Please, enter your last name." );
        }
    }
    
    private void emailValidation(String email) throws FormValidationException {
        if ( email != null && email.trim().length() != 0 ) 
        {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) 
            {
		throw new FormValidationException( "Invalid email address." );
            }
            else if(userService.findUserByEmail(email) != null )
            {
                throw new FormValidationException("Already used.");
            }
        }
        else
        {
            throw new FormValidationException( "Please, enter your email address." );
        }
    }
    
    private Date birthDateValidation(String birthDate) throws FormValidationException {
        if ( birthDate != null && birthDate.trim().length() != 0 ) 
        {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(birthDate);
                if(date.after(new Date())){
                    throw new FormValidationException( "Are you from the future ?" );
                }else {
                    return date;
                }
            } catch (ParseException ex) {
                throw new FormValidationException( "Please, enter a valid birth date." );
            }
        }else {
            throw new FormValidationException( "Please, enter your birth date." );
        }
    }
    
    private void postalCodeValidation(String postalCode) throws FormValidationException {
        if ( postalCode == null || postalCode.trim().length() == 0  ) 
        {
            throw new FormValidationException( "Please, enter your postal code." );
        }
    }
    
    private void passwordValidation(String password, String cpassword) throws FormValidationException{
        if(password != null && password.trim().length() != 0 && cpassword != null && cpassword.trim().length() != 0)
        {
            if(!password.equals(cpassword))
            {
                throw new FormValidationException("Passwords don't mach.");
            } else if (password.trim().length() < 6)
            {
                throw new FormValidationException("Your password must have at least 6 characters.");
            }
        }
        else
        {
            throw new FormValidationException("Please, enter and confirm your password.");
        }
    }
    
    private static String getFieldValue( HttpServletRequest request, String fieldName ) throws UnsupportedEncodingException {
        request.setCharacterEncoding( "UTF-8" );
        String value = request.getParameter( fieldName );
        if ( value == null || value.trim().length() == 0 ) {
            return null;
        } else {
            return value.trim();
        }
    }
    
}