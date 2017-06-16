/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.servlet;

import com.transcode.entity.File;
import com.transcode.entity.User;
import com.transcode.service.FileService;
import com.transcode.service.TranscodeService;
import com.transcode.service.UploadService;
import static com.transcode.servlet.HomeServlet.ATT_SESSION_USER;
import static com.transcode.servlet.HomeServlet.VIEW;
import static com.transcode.servlet.HomeServlet.VIEW_LOGIN;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author myriam
 */
@MultipartConfig
@WebServlet(name = "VideoSelectionServlet", urlPatterns = {"/videoSelection"})
public class VideoSelectionServlet extends HttpServlet{
    
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String VIEW             = "/WEB-INF/videoSelection.jsp";
    public static final String VIEW_LOGIN = "/logIn";
    public static final String BASE_PATH = "d:/Supinfo Courses/TranscodeOfficial/Transcode/Files/";
    public static final String TEMP_BASE_PATH = "d:/Supinfo Courses/TranscodeOfficial/Transcode/Pile/";
    
    @EJB
    TranscodeService transcodeService;
    
    @EJB
    UploadService uploadService;
    
    @EJB
    FileService fileService;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
        }catch (IOException | ServletException e)
        {
            System.out.println(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error");
        }
        
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                HttpSession session = req.getSession();
                User user = (User)session.getAttribute(ATT_SESSION_USER);
                if(user != null)
                {
                    Part filePart = req.getPart("file");
                byte[] file = IOUtils.toByteArray(filePart.getInputStream());
                String path = uploadService.upload(file, filePart.getSubmittedFileName(), req);
                String fileType = req.getParameter("fileType");
                /*String[] parts = path.split("\\.");*/
                String[] parts = filePart.getSubmittedFileName().split("\\.");
                String output = parts[0] + "." + fileType;
                String length = transcodeService.transcode(filePart.getSubmittedFileName(), output);
                    java.io.File fileToDelete = new java.io.File(TEMP_BASE_PATH + filePart.getSubmittedFileName());
                    fileToDelete.delete();
                if(length != null){
                    java.io.File fileToAdd = new java.io.File(BASE_PATH + output);
                    Path pathToFile = Paths.get(BASE_PATH + output);
                    byte[] data = Files.readAllBytes(pathToFile);
                    File resultFile = new File();
                    resultFile.setName(output);
                    resultFile.setType(fileType);
                    resultFile.setSize(fileToAdd.length()/1024/1024);
                    resultFile.setLength(length);
                    resultFile.setDate(new Date());
                    resultFile.setPath(BASE_PATH + output);
                    resultFile.setOwner(user);
                    fileService.addFile(resultFile);
                }
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
