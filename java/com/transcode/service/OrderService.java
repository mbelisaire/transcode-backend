/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import com.transcode.dao.OrderDao;
import com.transcode.entity.Order;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author myriam
 */
@Stateless
public class OrderService {
    
    @EJB
    private OrderDao orderDao;
    
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }
    
    public void removeOrder(Order order) {
        orderDao.removeOrder(order);
    }
    
}
