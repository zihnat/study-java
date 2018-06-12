package net.lessons.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

  public List<CarDTO> getAllCars()
  throws DAOException{
    List<CarDTO> result = null;
    Statement st = null;
    ResultSet rs = null;
    try{
      st = connection.createStatement();
      String query = "select id, mark, model from cars";
      rs = st.executeQuery(query);
      result = new ArrayList<CarDTO>();
      while(rs.next()){
        CarDTO dto = new CarDTO(rs.getInt("id"), rs.getString("mark"), rs.getString("model"));
        result.add(dto);
      }
    }catch(Exception e){
      throw new DAOException("Can't get list", e);
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet", e);
      }finally{
        return result;
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
    }catch(Exception e){
      throw new DAOException("Can't add record", e);
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet", e);
      }finally{
        return result;
      }
    }
  }

}
