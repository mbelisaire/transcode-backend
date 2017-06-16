/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao;

import com.transcode.entity.File;
import com.transcode.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author myriam
 */
@Local
public interface FileDao {
    public void addFile(File file);
    public void removeFile(File file);
    public List<File> getAllUserFiles(User owner);
    public File findFileById(Long fileId);
}
