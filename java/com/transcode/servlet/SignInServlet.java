/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

import com.transcode.entity.User;
import com.transcode.form.SignInForm;
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
@WebServlet(name = "SignInServlet", urlPatterns = {"/signIn"})
public class SignInServlet extends HttpServlet{
    
    public static final String VIEW = "/WEB-INF/signIn.jsp";
    public static final String VIEW_SUCCESS = "/home";
    public static final String ATT_FORM = "formSignIn";
    public static final String ATT_USER = "user";
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String ATT_PASSWORD = "password";
    public static final String PASSWORD_FIELD = "password";
    public static final String SUCCESS_VIEW = "/logIn";
    public static final String FAILURE_VIEW = "/WEB-INF/signIn.jsp";
    
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SignInForm form = new SignInForm(userService);
            User user = form.addUser(req);

            req.setAttribute(ATT_FORM, form);
            req.setAttribute(ATT_USER, user);
            String password = req.getParameter(PASSWORD_FIELD);
            req.setAttribute(ATT_PASSWORD, password);

            if(form.getErrors().isEmpty())
            {
                userService.addUser(user);
                this.getServletContext().getRequestDispatcher(SUCCESS_VIEW).forward(req, resp);
            }
            else
            {
                this.getServletContext().getRequestDispatcher(FAILURE_VIEW).forward(req, resp);
            }
        }catch (ServletException | IOException e)
        {
            System.out.println(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error");
        }
    }
    
}
