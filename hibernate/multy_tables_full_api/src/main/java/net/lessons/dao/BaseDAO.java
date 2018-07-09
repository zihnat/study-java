package net.lessons.dao;

import java.io.*;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {
  protected SessionFactory sessionFactory = null;

    public BaseDAO()
    throws DAOException{
      try{
          sessionFactory = new Configuration().configure().buildSessionFactory();
      }catch(Exception e){
        throw new DAOException ("Can't open session factory", e);
      }
    }

    public void close(){
      sessionFactory.close();
    }

    protected abstract String getSelectQuery();
    protected abstract T getObject(Session session, int id);

    public List<T> getAll()
    throws DAOException{
      Session session = null;
      try{
        session = sessionFactory.openSession();
        Query query = session.createQuery(getSelectQuery());
        List<T> objs = (List<T>)query.list();
        return objs;
      }catch(Exception e){
        throw new DAOException("Can't get list of rows", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }

    public T getById(int id)
    throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        //T origin = (T)session.get(T.class, id);
        T origin = getObject(session, id);
        return origin;
      }catch(Exception e){
        throw new DAOException("Can't get row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }

    public void add(T obj)
    throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(obj);
        session.getTransaction().commit();
      }catch(Exception e){
        Transaction tr = session.getTransaction();
        if(tr != null){
            tr.rollback();
        }
        throw new DAOException("Can't create row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }

    public void update(T obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(obj);
        session.getTransaction().commit();
      }catch(Exception e){
        Transaction tr = session.getTransaction();
        if(tr != null){
            tr.rollback();
        }
        throw new DAOException("Can't update row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }


    public void delete(int id) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();

        T origin = getObject(session, id);
        session.delete(origin);

        session.getTransaction().commit();
      }catch(Exception e){
        Transaction tr = session.getTransaction();
        if(tr != null){
            tr.rollback();
        }
        throw new DAOException("Can't delete row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }

}
