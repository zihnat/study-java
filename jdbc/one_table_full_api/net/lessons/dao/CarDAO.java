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
      throw new DAOException("Can't connect to db");
    }
  }

  public void close()
  throws DAOException{
    try{
      connection.close();
    }catch(Exception e){
      throw new DAOException("Exception occured while closing connection");
    }
  }

  //base

  public List<CarDTO> getAllCars()
  throws DAOException{
    List<CarDTO> result = new ArrayList<CarDTO>();
    Statement st = null;
    ResultSet rs = null;
    DAOException isException = null;
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
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

  public int addCar(String newMark, String newModel)
  throws DAOException, SQLException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Integer result = -1;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "insert into cars (mark, model) values (\"" + newMark + "\", \"" + newModel + "\")";
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next()){
        result=rs.getInt(1);
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

 //other

  public CarDTO getCarById(int id)
  throws DAOException{
    Statement st = null;
    ResultSet rs = null;
    CarDTO result = null;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "select id, mark, model from cars where id = " + id;
      rs = st.executeQuery(query);
      if(rs.next()){
        result = new CarDTO(rs.getInt("id"), rs.getString("mark"), rs.getString("model"));
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

  public boolean updateCar(int id, String newMark, String newModel)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "update cars set mark = \"" + newMark + "\", model = \"" + newModel + "\" where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next() && rs.getInt(1) > 0){
        result = true;
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

  public boolean updateCarMark(int id, String newMark)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "update cars set mark = \"" + newMark + "\" where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next() && rs.getInt(1) > 0){
        result = true;
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

  public boolean updateCarModel(int id, String newModel)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "update cars set model = \"" + newModel + "\" where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      rs = st.getGeneratedKeys();
      if (rs.next() && rs.getInt(1) > 0){
        result = true;
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }

  public boolean deleteCar(int id)
  throws DAOException{
    Statement st = null;
    Integer num = 0;
    ResultSet rs = null;
    Boolean result = false;
    DAOException isException = null;
    try{
      st = connection.createStatement();
      String query = "delete from cars where id = " + id;
      num = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      if(num > 0){
        result = true;
      }
    }catch(Exception e){
      isException = new DAOException("Can't get list");
    }finally{
      try{
        if(null != rs){
          rs.close();
        }
        if(null != st){
          st.close();
        }
        if(null == isException){
          return result;
        }else{
          throw isException;
        }
      }catch(Exception e){
        throw new DAOException("Can't close Statement or ResultSet");
      }
    }
  }
}
