package net.lessons.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.LazyInitializationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.lessons.dao.Company;
import net.lessons.dao.Car;
import net.lessons.dao.DAO;
import net.lessons.dao.DAOException;
import net.lessons.dao.DAOFactory;
import net.lessons.dao.Service;

public class DAOTest{
  //private static SessionFactory sessionFactory = null;
  private static DAOFactory factory = null;

  @BeforeClass
  public static void initSessionFactory() throws Exception{
    try{
      System.out.println("BEFORE CLASS CONNECTION TEST");
      //sessionFactory = new Configuration().configure().buildSessionFactory();
      factory = new DAOFactory();
      System.out.println("BEFORE CLASS CONNECTION TEST END");
    }catch(Exception e){
      System.out.println("BEFORE CLASS CONNECTION TEST EXCEPTION");
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void testSelectRow() throws DAOException{
    try{
      System.out.println("SELECT TEST");
      DAO compDAO = factory.getDAO(Company.class);
      compDAO.openSessionWithTransaction();
      Company comp = new Company("Company 1");
      compDAO.add(comp);
      comp = new Company("Company 2");
      compDAO.add(comp);
      comp = new Company("Company 3");
      compDAO.add(comp);
      List<Car> list = compDAO.getAll();
      for(int i = 0; i < list.size(); i++){
        System.out.println(list.get(i));
      }
      System.out.println("SELECT TEST END");
      compDAO.closeSessionRollbackTransaction();
    }catch(DAOException e){
      System.out.println("SELECT TEST EXCEPTION");
      e.printStackTrace();
      throw e;
    }
  }


  @Test
  public void testAddRow() throws DAOException{
    Company comp = new Company( "Test company 1" );
    DAO compDAO = factory.getDAO(Company.class);
    compDAO.openSessionWithTransaction();
    compDAO.add(comp);
    compDAO.closeSessionRollbackTransaction();
  }

  @Test
  public void testSelectById() throws DAOException{
    Company comp = new Company( "Test company 1" );
    DAO compDAO = factory.getDAO(Company.class);
    compDAO.openSession();
    compDAO.add(comp);
    Company comp2 = (Company)compDAO.getById(comp.getId());
    assertEquals(comp.getId(), comp2.getId());
    assertEquals(comp.getName(), comp2.getName());
    compDAO.closeSession();
  }

  @Test
  public void testDeleteRow() throws DAOException{
    System.out.println("DELETE TEST");
    Company comp = new Company( "Test company 1" );
    DAO compDAO = factory.getDAO(Company.class);
    compDAO.openSessionWithTransaction();
    compDAO.add(comp);
    compDAO.delete(comp.getId());
    Company comp2 = (Company)compDAO.getById(comp.getId());
    System.out.println("COMPANY AFTER DELETE: " + comp2);
    assertNull(comp2);
    compDAO.closeSessionRollbackTransaction();
    System.out.println("DELETE TEST END");
  }

  @Test
  public void testUpdateRow() throws DAOException{
    System.out.println("UPDATE TEST");
    Company comp = new Company( "Test company 1" );
    DAO compDAO = factory.getDAO(Company.class);
    compDAO.openSessionWithTransaction();
    compDAO.add(comp);
    System.out.println("COMPANY BEFORE UPDATE: " + comp);
    comp.setName("Update company name");
    compDAO.update(comp);
    Company comp2 = (Company)compDAO.getById(comp.getId());
    System.out.println("COMPANY AFTER UPDATE: " + comp2);
    assertEquals(comp.getName(), comp2.getName());
    compDAO.closeSessionRollbackTransaction();
    System.out.println("UPDATE TEST END");
  }

}
