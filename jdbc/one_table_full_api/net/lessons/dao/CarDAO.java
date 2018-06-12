package net.lessons.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import net.lessons.dao.CarDTO;
import net.lessons.dao.DAOException;

public class CarDAO {
  private Connection connection;

  public CarDAO()
  throws DAOException{
    try{
      Class.forName("com.mysql.jdbc.Driver");
      String connectionURL = "jdbc:mysql://localhost:3306/example";
      connection = DriverManager.getConnection(connectionURL, "user", "passwordForUser");
    }catch(Exception e){
      throw new DAOException("Can't connect to db", e);
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

  public int addCar(String newMark, String newModel)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Integer result = -1;
    try{
      st = connection.createStatement();
      String query = "insert into cars (mark, model) values (\"" + newMark + "\", \"" + newModel + "\")";
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

  public boolean updateCar(int id, String newMark, String newModel)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = "update cars set mark = \"" + newMark + "\", model = \"" + newModel + "\" where id = " + id;
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

  public boolean updateCarMark(int id, String newMark)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = "update cars set mark = \"" + newMark + "\" where id = " + id;
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

  public boolean updateCarModel(int id, String newModel)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = "update cars set model = \"" + newModel + "\" where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next() && rs.getInt(1) > 0){
        result = true;
      }
      return result;
    }catch(Exception e){
      throw new DAOException("Can't get update record", e);
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

  public boolean deleteCar(int id)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    try{
      st = connection.createStatement();
      String query = "delete from cars where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      if(num > 0){
        result = true;
      }
      return result;
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
