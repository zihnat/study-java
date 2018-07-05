package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarDAO extends BaseDAO <CarDTO> implements InterfaceDAO<CarDTO>{

    public CarDAO()
    throws DAOException{
      super();
    }

    public int add(CarDTO obj) 
    throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
        CarDTO cr = new CarDTO();
        cr.setMark(obj.getMark());
        cr.setModel(obj.getModel());
        session.save(cr);
        session.getTransaction().commit();
        return cr.getId();
        /*/
        session.save(obj);
        return obj.getId();//*/
      }catch(Exception e){
        Transaction tr = session.getTransaction();
        if(tr != null){
            tr.rollback();
        }
        throw new DAOException("Can't create row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
    }

    public boolean update(CarDTO obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
         
        CarDTO origin = (CarDTO) session.get(CarDTO.class, obj.getId());
        origin.setMark(obj.getMark());
        origin.setModel(obj.getModel());
         
        session.update(origin);
        session.getTransaction().commit();
        return true;
      }catch(Exception e){
        Transaction tr = session.getTransaction();
        if(tr != null){
            tr.rollback();
        }
        throw new DAOException("Can't update row", e);
      }finally{
        try{
          if(session != null){
            session.close();
          }
        }catch(Exception e){
          throw new DAOException("Can't close session", e);
        }
      }
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
