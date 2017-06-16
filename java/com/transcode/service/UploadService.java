/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author myriam
 */
@Stateless
public class UploadService {
    
    public String upload(byte[] file, String name, HttpServletRequest req){
        try {
            String basePath = "d:/Supinfo Courses/TranscodeOfficial/Transcode/Pile/";
            String path = basePath +  name;
            File pathFile = new File(path);
            try (FileOutputStream fos = new FileOutputStream(pathFile)) {
                fos.write(file);
            }
            return path;
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        
    }
}
