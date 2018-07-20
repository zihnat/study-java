package net.lessons.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import net.lessons.dao.Company;
import net.lessons.dao.Car;
import net.lessons.dao.DAO;
import net.lessons.dao.DAOException;
import net.lessons.dao.AppConfig;
import net.lessons.dao.CompanyDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DAOTest{
  private static AnnotationConfigApplicationContext context;

  @BeforeClass
  public static void initSessionFactory() throws Exception{
    try{
      context = new AnnotationConfigApplicationContext(AppConfig.class);
      System.out.println("BEFORE CLASS CONNECTION TEST");
      System.out.println("BEFORE CLASS CONNECTION TEST END");
    }catch(Exception e){
      System.out.println("BEFORE CLASS CONNECTION TEST EXCEPTION");
      throw e;
    }
  }

  @Test
  public void testSelectRow() throws DAOException{
    try{
      System.out.println("SELECT TEST");
      DAO compDAO = context.getBean(CompanyDAO.class);//factory.getDAO(Company.class);
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
      //e.printStackTrace();
      throw e;
    }
  }


  @Test
  public void testAddRow() throws DAOException{
    Company comp = new Company( "Test company 1" );
    DAO compDAO = context.getBean(CompanyDAO.class);
    compDAO.openSessionWithTransaction();
    compDAO.add(comp);
    compDAO.closeSessionRollbackTransaction();
  }

  @Test
  public void testSelectById() throws DAOException{
    Company comp = new Company( "Test company 1" );
    DAO compDAO = context.getBean(CompanyDAO.class);
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
    DAO compDAO = context.getBean(CompanyDAO.class);
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
    DAO compDAO = context.getBean(CompanyDAO.class);
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
