package net.lessons.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompanyDAO extends DAOImpl<CompanyDTO>{

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

}
