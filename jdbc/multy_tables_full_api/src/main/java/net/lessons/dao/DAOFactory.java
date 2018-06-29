package net.lessons.dao;

import java.util.HashMap;

public class DAOFactory{

  private HashMap<Class, BaseDAO> creators;

  public DAOFactory() throws DAOException{
    creators = new HashMap<Class, BaseDAO>();
    creators.put(CarDTO.class, new CarDAO());
    creators.put(CompanyDTO.class, new CompanyDAO());
  }

  public InterfaceDAO getDAO(Class dto) throws DAOException{
    BaseDAO res = creators.get(dto);
    if(res == null){
      throw new DAOException ("DAO object for " + dto + " not found");
    }
    return res;
  }
}
