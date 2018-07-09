package net.lessons.dao;

import java.util.HashMap;

public class DAOFactory{

  private HashMap<Class, DAO> creators;

  public DAOFactory() throws DAOException{
    creators = new HashMap<Class, DAO>();
    creators.put(Car.class, new CarDAO());
    creators.put(Company.class, new CompanyDAO());
    creators.put(Service.class, new ServiceDAO());
  }

  public DAO getDAO(Class dto) throws DAOException{
    DAO res = creators.get(dto);
    if(res == null){
      throw new DAOException ("DAO object for " + dto + " not found");
    }
    return res;
  }
}
