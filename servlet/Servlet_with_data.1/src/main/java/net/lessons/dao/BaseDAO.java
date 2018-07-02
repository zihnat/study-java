package net.lessons.dao;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {
  protected Connection connection;

  public BaseDAO()
  throws DAOException {
    FileInputStream is = null;
    Properties property = new Properties();
    try{
      property.load(this.getClass().getResourceAsStream("/config.properties"));
      String login = property.getProperty("db.login");
      String pass = property.getProperty("db.password");
      String connectionURL = property.getProperty("db.host");
      Class.forName(property.getProperty("db.driver"));
      connection = DriverManager.getConnection(connectionURL, login, pass);
    }catch(Exception e){
      System.out.println(e);
      throw new DAOException("Can't connect to db", e);
    }finally{
      try{
        if(null != is){
          is.close();
        }
      }catch(Exception e){
        throw new DAOException("Can't connect DB, can't close FileInputStream", e);
      }
    }
  }

  public void close()
  throws DAOException{
    try{
      connection.close();
    }catch(Exception e){
      throw new DAOException("Exception occured while closing connection", e);
    }
  }

  protected abstract String getSelectQuery();

  protected abstract String getAddQuery(T obj);

  protected abstract String getUpdateQuery(T obj);

  protected abstract String getDeleteQuery(int id);

  protected abstract T createDTO(ResultSet rs) throws DAOException;

  public List<T> getAll()
  throws DAOException{
    List<T> result = new ArrayList<T>();
    Statement st = null;
    ResultSet rs = null;
    try{
      st = connection.createStatement();
      String query = getSelectQuery();
      rs = st.executeQuery(query);
      while(rs.next()){
        T dto = createDTO(rs);
        result.add(dto);
      }
      return result;
    }catch(Exception e){
      throw new DAOException("Can't get list", e);
    }finally{
      DAOException exc = null;
      try{
        if(null != rs){
          rs.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close ResultSet", e);
      }
      try{
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close Statement", e);
      }
      if(null != exc){
        throw exc;
      }
    }
  }

  public T getById(int id)
  throws DAOException{
    Statement st = null;
    ResultSet rs = null;
    T result = null;
    try{
      st = connection.createStatement();
      String query = getSelectQuery() + " where id = " + id;
      rs = st.executeQuery(query);
      if(rs.next()){
        result = createDTO(rs);
      }
      return result;
    }catch(Exception e){
      throw new DAOException("Can't get record by ID", e);
    }finally{
      DAOException exc = null;
      try{
        if(null != rs){
          rs.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close ResultSet", e);
      }
      try{
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close Statement", e);
      }
      if(null != exc){
        throw exc;
      }
    }
  }

  public int add(T obj)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Integer result = -1;
    try{
      st = connection.createStatement();
      String query = getAddQuery(obj);
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next()){
        result=rs.getInt(1);
      }
      return result;
    }catch(Exception e){
      throw new DAOException("Can't add record", e);
    }finally{
      DAOException exc = null;
      try{
        if(null != rs){
          rs.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close ResultSet", e);
      }
      try{
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close Statement", e);
      }
      if(null != exc){
        throw exc;
      }
    }
  }

  public boolean update(T obj)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = getUpdateQuery(obj);
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next() && rs.getInt(1) > 0){
        result = true;
      }
      return result;
    }catch(Exception e){
      throw new DAOException("Can't update record", e);
    }finally{
      DAOException exc = null;
      try{
        if(null != rs){
          rs.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close ResultSet", e);
      }
      try{
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close Statement", e);
      }
      if(null != exc){
        throw exc;
      }
    }
  }

  public void delete(int id)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    try{
      st = connection.createStatement();
      String query = getDeleteQuery(id);
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
    }catch(Exception e){
      throw new DAOException("Can't delete record", e);
    }finally{
      DAOException exc = null;
      try{
        if(null != rs){
          rs.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close ResultSet", e);
      }
      try{
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        exc = new DAOException("Can't close Statement", e);
      }
      if(null != exc){
        throw exc;
      }
    }
  }

}
