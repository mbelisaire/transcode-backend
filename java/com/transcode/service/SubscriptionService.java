/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import com.transcode.dao.SubscriptionDao;
import com.transcode.entity.Subscription;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author myriam
 */
@Stateless
public class SubscriptionService {
    
    @EJB
    private SubscriptionDao subscriptionDao;
    
    public void addSubscription(Subscription subscription) {
        subscriptionDao.addSubscription(subscription);
    }
    
    public void removeSubscription(Subscription subscription) {
        subscriptionDao.removeSubscription(subscription);
    }
    
}
