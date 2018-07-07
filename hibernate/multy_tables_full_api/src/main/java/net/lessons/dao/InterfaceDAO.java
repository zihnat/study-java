package net.lessons.dao;

import java.util.*;

public interface InterfaceDAO<T>{

  public List<T> getAll() throws DAOException;

  public T getById(int id) throws DAOException;

  public void add(T obj) throws DAOException;

  public boolean update(T obj) throws DAOException;

  public void delete(int id) throws DAOException;

  public void close() throws DAOException;

}
