package net.lessons.dao;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import net.lessons.dao.CarDTO;
import net.lessons.dao.DAOException;

public class CarDAO {
  private Connection connection;

  public CarDAO()
  throws DAOException{
    FileInputStream is = null;
    Properties property = new Properties();
    try{
      property.load(this.getClass().getResourceAsStream("/db.properties"));
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

  //base
//*
  public List<CarDTO> getAllCars()
  throws DAOException{
    List<CarDTO> result = new ArrayList<CarDTO>();
    Statement st = null;
    ResultSet rs = null;
    try{
      st = connection.createStatement();
      String query = "select id, mark, model from cars";
      rs = st.executeQuery(query);
      while(rs.next()){
        CarDTO dto = new CarDTO(rs.getInt("id"), rs.getString("mark"), rs.getString("model"));
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

  public int addCar(CarDTO car)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Integer result = -1;
    try{
      st = connection.createStatement();
      String query = "insert into cars (mark, model) values (\"" + car.getMark() + "\", \"" + car.getModel() + "\")";
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

 //other

  public CarDTO getCarById(int id)
  throws DAOException{
    Statement st = null;
    ResultSet rs = null;
    CarDTO result = null;
    try{
      st = connection.createStatement();
      String query = "select id, mark, model from cars where id = " + id;
      rs = st.executeQuery(query);
      if(rs.next()){
        result = new CarDTO(rs.getInt("id"), rs.getString("mark"), rs.getString("model"));
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

  public boolean updateCar(CarDTO car)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = "update cars set mark = \"" + car.getMark() + "\", model = \"" + car.getModel() + "\" where id = " + car.getId();
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

  public void deleteCar(int id)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    try{
      st = connection.createStatement();
      String query = "delete from cars where id = " + id;
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
