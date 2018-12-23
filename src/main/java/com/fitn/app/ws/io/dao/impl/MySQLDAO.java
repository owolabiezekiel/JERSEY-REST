/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.io.dao.impl;

import com.fitn.app.ws.io.dao.DAO;
import com.fitn.app.ws.io.entity.TransactionEntity;
import com.fitn.app.ws.io.entity.UserEntity;
import com.fitn.app.ws.shared.dto.TransactionDTO;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author owoez
 */
public class MySQLDAO implements DAO {

    Session session;

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public UserDTO getUserByUsername(String userName) {
        UserDTO userDTO = null;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        // Fetch single result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
        }

        return userDTO;
    }
    
    @Override
    public UserDTO getUserByAccountNumber(String accountNumber) {
        UserDTO userDTO = null;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("accountNumber"), accountNumber));

        // Fetch single result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
        }

        return userDTO;
    }

    @Override
    public UserDTO getUser(String id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("userId"), id));

        // Fetch single result
        UserEntity userEntity = session.createQuery(criteria).getSingleResult();

        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public void closeConnection() {
        if (session != null) {
            session.close();
        }
    }
    
    @Override
    public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();

        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public void updateUser(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);

        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        SessionFactory sessionFactoryLocal = HibernateUtils.getSessionFactory();
        Session sessionLocal = sessionFactoryLocal.openSession();
        CriteriaBuilder cb = sessionLocal.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entities
        Root<UserEntity> userRoot = criteria.from(UserEntity.class);
        criteria.select(userRoot);

        // Fetch results from start to a number of "limit"
        List<UserEntity> searchResults = sessionLocal.createQuery(criteria).
                setFirstResult(start).
                setMaxResults(limit).
                getResultList();

        List<UserDTO> returnValue = new ArrayList<UserDTO>();
        for (UserEntity userEntity : searchResults) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public void deleteUser(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);
        
        session.beginTransaction();
        session.delete(userEntity);
        session.getTransaction().commit();
    }

    @Override
    public long getLastID() {
        List<UserDTO> users = this.getUsers(0, 1000000000);
        int size = users.size();
        if (size == 0){
            return 1;
        }
        else {
            UserDTO lastUser = users.get(size - 1);
            long lastID = lastUser.getId();
            return  lastID + 1; 
        }
             
    }

    @Override
    public TransactionDTO saveTransaction(TransactionDTO transaction) {
        TransactionDTO returnValue = null;
        TransactionEntity transactionEntity = new TransactionEntity();
        BeanUtils.copyProperties(transaction, transactionEntity);
        
        session.beginTransaction();
        session.save(transactionEntity);
        session.getTransaction().commit();
        
        returnValue = new TransactionDTO();
        BeanUtils.copyProperties(transactionEntity, returnValue);
        return returnValue;
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        SessionFactory sessionFactoryLocal = HibernateUtils.getSessionFactory();
        Session sessionLocal = sessionFactoryLocal.openSession();
        CriteriaBuilder cb = sessionLocal.getCriteriaBuilder();
        
        CriteriaQuery<TransactionEntity> criteria = cb.createQuery(TransactionEntity.class);
         
        Root<TransactionEntity> transactionRoot = criteria.from(TransactionEntity.class);
        criteria.select(transactionRoot);
        
        List<TransactionEntity> searchResults = sessionLocal.createQuery(criteria).getResultList();
        
        
        List<TransactionDTO> returnValue = new ArrayList<TransactionDTO>();
        for (TransactionEntity transactionEntity : searchResults) {
            TransactionDTO transactionDTO = new TransactionDTO();
            BeanUtils.copyProperties(transactionEntity, transactionDTO);
            returnValue.add(transactionDTO);
        }

        return returnValue;
    }

    @Override
    public List<TransactionDTO> getTransactionByAccountNumber(String accnumber) {
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TransactionEntity> criteria = cb.createQuery(TransactionEntity.class);      
        Root<TransactionEntity> profileRoot = criteria.from(TransactionEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("fromAccount"), accnumber));

        Query<TransactionEntity> query = session.createQuery(criteria);
        List<TransactionEntity> resultList = query.getResultList();
        if (resultList != null) {
            for (TransactionEntity transactionEntity : resultList) {
                TransactionDTO transactionDTO = new TransactionDTO();
                BeanUtils.copyProperties(transactionEntity, transactionDTO);
                transactionDTOs.add(transactionDTO);
            }
        }
        
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("toAccount"), accnumber));

        query = session.createQuery(criteria);
        resultList = query.getResultList();
        if (resultList != null) {
            for (TransactionEntity transactionEntity : resultList) {
                TransactionDTO transactionDTO = new TransactionDTO();
                BeanUtils.copyProperties(transactionEntity, transactionDTO);
                transactionDTOs.add(transactionDTO);
            }
        }
        
        return transactionDTOs;
        
    }

    
    
    
    

}
