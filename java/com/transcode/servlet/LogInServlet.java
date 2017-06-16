/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

import com.transcode.entity.User;
import com.transcode.form.LogInForm;
import com.transcode.service.UserService;
import java.io.IOException;
import javax.ejb.EJB;
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
@WebServlet(name = "LogInServlet", urlPatterns = {"/logIn"})
public class LogInServlet extends HttpServlet{
    
    public static final String VIEW = "/WEB-INF/logIn.jsp";
    public static final String VIEW_SUCCESS = "/home";
    public static final String ATT_FORM = "formLogIn";
    public static final String ATT_USER = "user";
    public static final String ATT_SESSION_USER = "sessionUser";
    
    @EJB
    UserService userService;
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        
        try
        {
          HttpSession session = request.getSession();
          if(session.getAttribute(ATT_SESSION_USER) != null)
          {
              this.getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
          }
          else
          {
              this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
          }
        }catch (IOException | ServletException e)
        {
            System.out.println(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error");
        }
       
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
      try
      {
          LogInForm form = new LogInForm(userService);
          User user = form.LogInUser(req);
          
          HttpSession session = req.getSession();
          req.setAttribute(ATT_FORM, form);
          req.setAttribute(ATT_USER, user);
          
          if(form.getErrors().isEmpty())
          {
              session.setAttribute(ATT_SESSION_USER, user);
              resp.sendRedirect(req.getContextPath() + VIEW_SUCCESS);
          }
          else
          {
              session.setAttribute(ATT_SESSION_USER, null);
              this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
          }
      }catch (IOException | ServletException e)
      {
          System.out.println(e.getMessage());
          resp.sendRedirect(req.getContextPath() + "/error");
      }
      
    }
}
