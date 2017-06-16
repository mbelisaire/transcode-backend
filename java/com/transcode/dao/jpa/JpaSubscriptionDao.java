/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao.jpa;

import com.transcode.dao.SubscriptionDao;
import com.transcode.entity.Subscription;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author myriam
 */
@Stateless
public class JpaSubscriptionDao implements SubscriptionDao{
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addSubscription(Subscription subscription) {
        em.persist(subscription);
    }
    @Override
    public void removeSubscription (Subscription subscription) {
        em.remove(em.merge(subscription));
    }
}
