/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.dao.jpa;

import com.transcode.dao.FileDao;
import com.transcode.entity.File;
import com.transcode.entity.File_;
import com.transcode.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author myriam
 */
@Stateless
public class JpaFileDao implements FileDao{
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addFile(File file) {
        em.persist(file);
    }
    @Override
    public void removeFile(File file) {
        em.remove(em.merge(file));
    }
    
    @Override
    public List<File> getAllUserFiles(User owner) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(File.class);
        Root<File> file = query.from(File.class);
        query.where(criteriaBuilder.equal(file.get(File_.owner), owner));
        return em.createQuery(query).getResultList();
    }
    
    @Override
    public File findFileById(Long fileId) {
        return em.find(File.class, fileId);
    }
}
