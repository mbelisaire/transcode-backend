/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao.jpa;

import com.transcode.dao.OrderDao;
import com.transcode.entity.Order;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author myriam
 */
@Stateless
public class JpaOrderDao implements OrderDao{
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addOrder (Order order) {
        em.persist(order);
    }
    @Override
    public void removeOrder(Order order) {
        em.remove(em.merge(order));
    }
}
