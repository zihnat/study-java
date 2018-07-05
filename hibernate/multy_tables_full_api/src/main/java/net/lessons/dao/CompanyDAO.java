package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompanyDAO extends BaseDAO<CompanyDTO>{

  public CompanyDAO()
  throws DAOException{}

    @Override
    protected String getSelectQuery() {
        return "from CompanyDTO";
    }

    @Override
    protected CompanyDTO getObject(Session session, int id) {
        return (CompanyDTO)session.get(CompanyDTO.class, id);
    }

    public int add(CompanyDTO obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
        CompanyDTO origin = new CompanyDTO();
        origin.setName(obj.getName());
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

    public boolean update(CompanyDTO obj) throws DAOException {
      Session session = null;
      try{
        session = sessionFactory.openSession();
        session.beginTransaction();
         
        CompanyDTO origin = (CompanyDTO) session.get(CompanyDTO.class, obj.getId());
        origin.setName(obj.getName());
         
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
