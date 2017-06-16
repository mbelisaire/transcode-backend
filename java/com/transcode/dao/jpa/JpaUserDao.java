/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao.jpa;

import com.transcode.dao.UserDao;
import com.transcode.entity.User;
import com.transcode.entity.User_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author myriam
 */
@Stateless
public class JpaUserDao implements UserDao{
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     *
     * @param user
     * @return
     */
    @Override
    public User addUser(User user){
        em.persist(user);
        return user;
    }
    
    @Override
    public void updateUser (User user){
        em.merge(user);
    }
    
    @Override
    public List<User> getAllUsers(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(User.class);
        query.from(User.class);
        return em.createQuery(query).getResultList();
    }
    
    @Override
    public User findUserById(Long userId){
        return em.find(User.class, userId);
    }
    
    @Override 
    public User findUserByUsername (String username){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.where(criteriaBuilder.equal(user.get(User_.username), username));
        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e)
        {
            return null;
        }
    }
    
    @Override
    public User findUserByEmail (String email) {
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.where(criteriaBuilder.equal(user.get(User_.email), email));
        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e)
        {
            return null;
        }
    }
    
    @Override
    public void removeUser(User user){
        em.remove(em.merge(user));
    }
}
