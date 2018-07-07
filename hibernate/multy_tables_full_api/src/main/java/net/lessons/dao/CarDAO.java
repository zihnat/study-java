package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarDAO extends BaseDAO <CarDTO> implements InterfaceDAO<CarDTO>{

    public CarDAO()
    throws DAOException{
      super();
    }

    @Override
    protected String getSelectQuery() {
        return "from CarDTO";
    }

    @Override
    protected CarDTO getObject(Session session, int id) {
        return (CarDTO)session.get(CarDTO.class, id);
    }

}
