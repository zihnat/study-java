package net.lessons.dao;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class CarDAO extends BaseDAO <CarDTO>{

  public CarDAO()
  throws DAOException{}

  @Override
  protected String getSelectQuery(){
      return "select id, mark, model from cars";
  }

  @Override
  protected CarDTO createDTO(ResultSet rs)
  throws DAOException{
    try{
      return new CarDTO(rs.getInt("id"), rs.getString("mark"), rs.getString("model"));
    }catch(Exception e){
      throw new DAOException("Can't create DTO from result set", e);
    }
  }

  @Override
  protected String getAddQuery(CarDTO obj){
    return "insert into cars (mark, model) values (\"" + obj.getMark() + "\", \"" + obj.getModel() + "\")";
  }

  @Override
  protected String getUpdateQuery(CarDTO obj){
    return "update cars set mark = \"" + obj.getMark() + "\", model = \"" + obj.getModel() + "\" where id = " + obj.getId();
  }

  @Override
  protected String getDeleteQuery(int id){
    return "delete from cars where id = " + id;
  }


}
