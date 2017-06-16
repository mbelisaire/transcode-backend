/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

import com.transcode.entity.File;
import com.transcode.entity.User;
import com.transcode.service.FileService;
import java.io.IOException;
import java.util.Objects;
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
@WebServlet(name = "DeleteFileServlet", urlPatterns = {"/delete/*"})
public class DeleteFileServlet extends HttpServlet{
    
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String VIEW_LOGIN       = "/logIn";
    public static final String VIEW             = "/files";
    public static final String BASE_PATH = "d:/Supinfo Courses/TranscodeOfficial/Transcode/Files/";
    
    @EJB
    FileService fileService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession(); 
            User user = (User)session.getAttribute(ATT_SESSION_USER);
            if(user != null)
            {
                Long fileId = Long.parseLong(request.getPathInfo().substring(1));
                File file = fileService.findFileById(fileId);
                if(Objects.equals(file.getOwner().getId(), user.getId()))
                {
                    fileService.removeFile(file);
                    java.io.File fileToDelete = new java.io.File(BASE_PATH + file.getName());
                    fileToDelete.delete();
                }
                response.sendRedirect(request.getContextPath() + VIEW);
            }
            else
            {
                this.getServletContext().getRequestDispatcher( VIEW_LOGIN ).forward( request, response );
            }
        }catch (ServletException | IOException e)
        {
            System.out.println(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
    
}
