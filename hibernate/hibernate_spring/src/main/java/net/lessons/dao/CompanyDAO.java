package net.lessons.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAO extends DAOImpl<Company>{

  public CompanyDAO()
  throws DAOException{}

    @Override
    protected String getSelectQuery() {
        return "from Company";
    }

    @Override
    protected Company getObject(Session session, int id) {
        return (Company)session.get(Company.class, id);
    }

}
