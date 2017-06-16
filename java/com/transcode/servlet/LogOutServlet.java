/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

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
@WebServlet(name = "LogOutServlet", urlPatterns = {"/logOut"})
public class LogOutServlet extends HttpServlet {

    public static final String URL_REDIRECTION = "/Transcode";
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Récupération et destruction de la session en cours.
        HttpSession session = request.getSession();
        session.invalidate();

        //Redirection vers la page d'index.
        response.sendRedirect(URL_REDIRECTION);
     }

}