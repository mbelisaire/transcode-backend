/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

import com.transcode.entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author myriam
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet{
    
    public static final String ATT_OBJECT       = "object";
    public static final String ATT_USER_OBJECTS = "userObjects";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String VIEW             = "/WEB-INF/profile.jsp";
    public static final String VIEW_LOGIN = "/logIn";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute(ATT_SESSION_USER);
            if(user != null)
            {
                this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
            }
            else
            {
                resp.sendRedirect(req.getContextPath() + VIEW_LOGIN);
            }
        }catch (IOException | ServletException e)
        {
            System.out.println(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error");
        }
        
        
    }
    
}
