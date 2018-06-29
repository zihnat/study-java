package net.lessons.dao;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class CompanyDAO extends BaseDAO<CompanyDTO>{

  public CompanyDAO()
  throws DAOException{}

  @Override
  protected String getSelectQuery(){
      return "select id, name from services";
  }

  @Override
  protected CompanyDTO createDTO(ResultSet rs)
  throws DAOException{
    try{
      return new CompanyDTO(rs.getInt("id"), rs.getString("name"));
    }catch(Exception e){
      throw new DAOException("Can't create DTO from result set", e);
    }
  }

  @Override
  protected String getAddQuery(CompanyDTO obj){
    return "insert into services (name) values (\"" + obj.getName() + "\")";
  }

  @Override
  protected String getUpdateQuery(CompanyDTO obj){
    return "update services set name = \"" + obj.getName() + "\" where id = " + obj.getId();
  }

  @Override
  protected String getDeleteQuery(int id){
    return "delete from services where id = " + id;
  }


}
