package net.lessons.dao;

import java.util.HashMap;

public class DAOFactory{

  private HashMap<Class, DAO> creators;

  public DAOFactory() throws DAOException{
    creators = new HashMap<Class, DAO>();
    creators.put(CarDTO.class, new CarDAO());
    creators.put(CompanyDTO.class, new CompanyDAO());
    creators.put(ServiceDTO.class, new ServiceDAO());
  }

  public DAO getDAO(Class dto) throws DAOException{
    DAO res = creators.get(dto);
    if(res == null){
      throw new DAOException ("DAO object for " + dto + " not found");
    }
    return res;
  }
}
