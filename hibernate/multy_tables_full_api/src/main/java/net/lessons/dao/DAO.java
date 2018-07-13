package net.lessons.dao;

import java.util.*;

public interface DAO<T>{

  public void openSession() throws DAOException;
  public void closeSession()throws DAOException;

  public void openSessionWithTransaction() throws DAOException;
  public void closeSessionCommitTransaction()throws DAOException;
  public void closeSessionRollbackTransaction()throws DAOException;

  public List<T> getAll() throws DAOException;

  public T getById(int id) throws DAOException;

  public void add(T obj) throws DAOException;

  public void update(T obj) throws DAOException;

  public void delete(int id) throws DAOException;

  public void close() throws DAOException;

}
