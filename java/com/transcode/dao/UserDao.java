/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao;

import com.transcode.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author myriam
 */
@Local
public interface UserDao {
     
    User addUser(User user);
    
    void updateUser (User user);
    
    List<User> getAllUsers();

    public User findUserById(Long userId);
    
    public User findUserByUsername(String username);
    
    public User findUserByEmail(String email);

    public void removeUser(User user);
}