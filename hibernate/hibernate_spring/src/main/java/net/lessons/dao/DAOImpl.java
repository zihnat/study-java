package net.lessons.dao;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class DAOImpl<T> implements DAO<T> {

    @Autowired
    protected SessionFactory sessionFactory = null;
    protected Session session = null;
    protected Transaction transaction = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DAOImpl()
    throws DAOException{
      try{
        //sessionFactory = new Configuration().configure().buildSessionFactory();
      }catch(Exception e){
        throw new DAOException ("Can't open session factory", e);
      }
    }

    @Override
    public void close() throws DAOException{
      DAOException exc = null;
      try{
        //System.out.println("TRANSACTION STATUS IS:" + transaction.getStatus());
        if(transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE){
          transaction.rollback();
        }
      }catch(Exception e){
        exc = new DAOException("Can't rollback transaction", e);
      }finally{
        try{
          if(session != null && session.isConnected()){
            session.close();
          }
        }catch(Exception e){
          exc = new DAOException("Can't close session while closing DAO", e);
        }
        try{
          sessionFactory.close();
        }catch(Exception e){
          exc = new DAOException("Can't close session factory", e);
        }
        if(exc != null){
          throw exc;
        }
      }
    }

    @Override
    public void openSession() throws DAOException{
      try{
        session = sessionFactory.openSession();
      }catch(Exception e){
        throw new DAOException("Can't open session", e);
      }
    }

    @Override
    public void closeSession()throws DAOException{
      try{
        session.close();
      }catch(Exception e){
        throw new DAOException("Can't close session", e);
      }
    }

    @Override
    public void openSessionWithTransaction() throws DAOException{
      try{
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
      }catch(Exception e){
        throw new DAOException("Can't open session", e);
      }
    }

    @Override
    public void closeSessionCommitTransaction()throws DAOException{
      try{
        transaction.commit();
      }catch(Exception e){
        throw new DAOException("Can't commit transaction", e);
      }
      try{
        session.close();
      }catch(Exception e){
        throw new DAOException("Can't close session", e);
      }
    }

    @Override
    public void closeSessionRollbackTransaction()throws DAOException{
      try{
        transaction.rollback();
      }catch(Exception e){
        throw new DAOException("Can't commit transaction", e);
      }
      try{
        session.close();
      }catch(Exception e){
        throw new DAOException("Can't close session", e);
      }
    }

    protected abstract String getSelectQuery();
    protected abstract T getObject(Session session, int id);

    @Override
    public List<T> getAll()
    throws DAOException{
      try{
        Query query = session.createQuery(getSelectQuery());
        List<T> objs = (List<T>)query.list();
        return objs;
      }catch(Exception e){
        throw new DAOException("Can't get list of rows", e);
      }
    }

    @Override
    public T getById(int id)
    throws DAOException {
      try{
        T origin = getObject(session, id);
        return origin;
      }catch(Exception e){
        throw new DAOException("Can't get row", e);
      }
    }

    @Override
    public void add(T obj)
    throws DAOException {
      try{
        session.save(obj);
      }catch(Exception e){
        throw new DAOException("Can't create row", e);
      }
    }

    @Override
    public void update(T obj) throws DAOException {
      try{
        session.update(obj);
      }catch(Exception e){
        throw new DAOException("Can't update row", e);
      }
    }

    @Override
    public void delete(int id) throws DAOException {
      try{
        T origin = getObject(session, id);
        session.delete(origin);
      }catch(Exception e){
        throw new DAOException("Can't delete row", e);
      }
    }

}
