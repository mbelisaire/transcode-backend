/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import com.transcode.dao.UserDao;
import com.transcode.entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author myriam
 */
@Stateless
public class UserService {
    
    @EJB
    private UserDao userDao;
    
    public void addUser(User user){
        
        userDao.addUser(user);
    }
    
    public void updateUser(User user)
    {
        userDao.updateUser(user);
    }
    
    public List<User> getAllUsers()
    {
        return userDao.getAllUsers();
    }
   
    public User findUserById(Long userId)
    {
        return userDao.findUserById(userId);
    }
    
    public User findUserByUsername(String username)
    {
        return userDao.findUserByUsername(username);
    }
    
    public User findUserByEmail(String email)
    {
        return userDao.findUserByEmail(email);
    }
    
    public void removeUser(User user){
        userDao.removeUser(user);
    }
}
