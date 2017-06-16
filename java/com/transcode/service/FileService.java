/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import com.transcode.dao.FileDao;
import com.transcode.entity.File;
import com.transcode.entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author myriam
 */
@Stateless
public class FileService {
    
    @EJB
    private FileDao fileDao;
    
    public void addFile(File file) {
        fileDao.addFile(file);
    }
    
    public void removeFile(File file) {
        fileDao.removeFile(file);
    }
    
    public List<File> getAllUserFiles(User owner) {
        return fileDao.getAllUserFiles(owner);
    }
    
    public File findFileById(Long fileId){
        return fileDao.findFileById(fileId);
    }
    
}
