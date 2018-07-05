package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceDAO extends BaseDAO<ServiceDTO>{

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

    public int add(ServiceDTO obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
        ServiceDTO origin = new ServiceDTO();
        origin.setDate(obj.getDate());
        origin.setPrice(obj.getPrice());
        origin.setCar(obj.getCar());
        origin.setCompany(obj.getCompany());
        
        session.save(origin);
        session.getTransaction().commit();
        return origin.getId();
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

    public boolean update(ServiceDTO obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
         
        ServiceDTO origin = (ServiceDTO) session.get(ServiceDTO.class, obj.getId());
        origin.setDate(obj.getDate());
        origin.setPrice(obj.getPrice());
        origin.setCar(obj.getCar());
        origin.setCompany(obj.getCompany());
         
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


}