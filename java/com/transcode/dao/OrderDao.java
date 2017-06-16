/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao;

import com.transcode.entity.Order;
import javax.ejb.Local;

/**
 *
 * @author myriam
 */
@Local
public interface OrderDao {
    public void addOrder(Order order);
    public void removeOrder(Order order);
}
