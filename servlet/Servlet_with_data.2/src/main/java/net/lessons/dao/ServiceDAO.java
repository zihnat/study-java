package net.lessons.dao;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

public class ServiceDAO extends BaseDAO<ServiceDTO>{

  public ServiceDAO()
  throws DAOException{}

  @Override
  protected String getSelectQuery(){
      return "select id, service_date, price, car_id, service_id from service";
  }

  @Override
  protected ServiceDTO createDTO(ResultSet rs)
  throws DAOException{
    try{
      return new ServiceDTO(
              rs.getInt("id"), 
              rs.getDate("service_date"), 
              rs.getFloat("price"), 
              rs.getInt("car_id"), 
              rs.getInt("service_id")
      );
    }catch(Exception e){
      throw new DAOException("Can't create DTO from result set", e);
    }
  }

  @Override
  protected String getAddQuery(ServiceDTO obj){
    return "insert into service (service_date, price, car_id, service_id) "
            +"values (\"" + obj.getDate() + "\", " + obj.getPrice() + ", " + obj.getCar() + ", " + obj.getCompany() + ")";
  }

  @Override
  protected String getUpdateQuery(ServiceDTO obj){
    return "update service set service_date = \"" + obj.getDate() + "\", price = " + obj.getPrice() + ", car_id = " + obj.getCar() + ", service_id = " + obj.getCompany() + " where id = " + obj.getId();
  }

  @Override
  protected String getDeleteQuery(int id){
    return "delete from service where id = " + id;
  }


}
