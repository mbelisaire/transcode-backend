/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao;

import com.transcode.entity.Subscription;
import javax.ejb.Local;

/**
 *
 * @author myriam
 */
@Local
public interface SubscriptionDao {
    public void addSubscription(Subscription subscription);
    public void removeSubscription (Subscription subscription);
}
