package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarDAO extends DAOImpl <Car>{

    public CarDAO()
    throws DAOException{
      super();
    }

    @Override
    protected String getSelectQuery() {
        return "from Car";
    }

    @Override
    protected Car getObject(Session session, int id) {
        return (Car)session.get(Car.class, id);
    }

}
