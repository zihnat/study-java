package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceDAO extends DAOImpl<ServiceDTO>{

  public ServiceDAO()
  throws DAOException{}

    @Override
    protected String getSelectQuery() {
        return "from ServiceDTO";
    }

    @Override
    protected ServiceDTO getObject(Session session, int id) {
        return (ServiceDTO)session.get(ServiceDTO.class, id);
    }

}
