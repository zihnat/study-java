package net.lessons.dao;

import org.hibernate.Session;

public class ServiceDAO extends DAOImpl<Service>{

  public ServiceDAO()
  throws DAOException{}

    @Override
    protected String getSelectQuery() {
        return "from Service";
    }

    @Override
    protected Service getObject(Session session, int id) {
        return (Service)session.get(Service.class, id);
    }

}
