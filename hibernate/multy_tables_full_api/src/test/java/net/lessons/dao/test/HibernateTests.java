package net.lessons.dao.test;

import java.time.LocalDate;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import net.lessons.dao.Service;


public class HibernateTests
{
   private static SessionFactory sessionFactory = null;

   @BeforeClass
   public static void setUp() throws Exception
   {
      sessionFactory = new Configuration().configure().buildSessionFactory();

   }

   @AfterClass
   public static void tearDown() throws Exception
   {
      sessionFactory.close();
   }

   @Test
   public void testSaveOperation()
   {
      System.out.println( "testSaveOperation begins ........ This is \"C\" of CRUD" );
      Company comp = new Company( "Test company 1" );
      Car car = new Car( "Lada", "Kalina");

      Session session = sessionFactory.openSession();
      session.beginTransaction();

      session.save( comp );
      session.save( car );

      session.getTransaction().commit();
      session.close();
      System.out.println( "testSaveOperation ends ......." );

   }

   @Test
   public void testRetriveOneCar()
   {
      System.out.println( "testRetriveOneCar begins .......This is \"R\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Car car = session.get( Car.class, 1L );

      session.getTransaction().commit();
      System.out.println( "Retrieved car from DB is " + car );
      session.close();
      assertEquals( 1L, car.getId() );
      System.out.println( "testRetriveOneCar ends ......." );

   }

   @Test
   public void testUpdateOneCompany()
   {
      System.out.println( "testRetriveOneCompany begins .......This is \"U\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Company comp = session.get( Company.class, 1L );
      System.out.println( "Retrieved company from DB is " + comp );
      comp.setName( "Updated test company" );
      session.update( comp );
      session.getTransaction().commit();
      System.out.println( "Updated the company, so retrieve one more time to confirm" );
      Company comp2 = session.get( Company.class, 1L );
      System.out.println( "Retrieved company from DB after updation is " + comp2 );
      session.close();
      assertEquals( 1L, comp.getId() );
      System.out.println( "testRetriveOnePerson ends ......." );

   }

   @Test
   public void testDeleteCompany()
   {
      System.out.println( "testDeleteCompany begins .......This is \"D\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Company comp = session.get( Company.class, 1L );
      System.out.println( "Retrieved company from DB is " + comp );
      session.delete( comp );
      session.getTransaction().commit();
      System.out.println( "Deleted the company, so retrieve one more time to confirm" );
      Company comp2 = session.get( Company.class, 1L );
      System.out.println( "Retrieved company from DB after deletion is " + comp2 );
      session.close();
      assertNull( comp2 );
      System.out.println( "testDeleteCompany ends ......." );

   }

   @Test
   public void testEmbeddedObjectWithAttributeOverride()
   {
      System.out.println( "testEmbeddedObjectWithAttributeOverride begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      LocalDate todayLocalDate = LocalDate.now();
      Service serv = new Service( java.sql.Date.valueOf(todayLocalDate), 10.99F, 1, new Company("New company") );

      Company comp = new Company( "Update company" );

      serv.setCompany(comp);

      session.save( serv );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testEmbeddedObjectWithAttributeOverride ends ......." );

   }

   @Test
   public void testElementCollection()
   {
      System.out.println( "testElementCollection begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Car car = new Car("VW", "Passat");
      session.save( car );

      LocalDate todayLocalDate = LocalDate.now();
      Set<Service> services = new HashSet<Service>();
      Company comp = new Company("Test company");
      Service serv = new Service( java.sql.Date.valueOf(todayLocalDate), 10.99F, car.getId(), comp );
      services.add(serv);
      serv = new Service( java.sql.Date.valueOf(todayLocalDate), 10.99F, car.getId(), comp );
      services.add(serv);
      car.setServices(services);

      session.save( car );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testElementCollection ends ......." );

   }

   @Test( expected = LazyInitializationException.class )
   public void testLazyLoading()
   {
      System.out.println( "testLazyLoading begins ......." );
      testElementCollection();
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Car car = session.get( Car.class, 1L );
      session.getTransaction().commit();
      session.close();
      System.out.println( "Test case is passed if the LazyInitializationException occurs, if there is no expception next line will have the list of insurances" );
      System.out.println( car.getServices() );
      System.out.println( "testLazyLoading ends ......." );
   }

   @Test
   public void testOneToManyMapping()
   {
      System.out.println( "testOneToManyMapping begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Car car = new Car( "VW", "Passat" );
      Company comp = new Company("Auto repaire");
      Service s1 = new Service();
      s1.setCompany(comp);
      s1.setPrice(10F);
      Service s2 = new Service();
      s2.setCompany(comp);
      s2.setPrice(20F);
      Set<Service> services = new HashSet<Service>();
      services.add(s1);
      services.add(s2);
      car.setServices(services);
      session.save( comp );
      session.save( s1 );
      session.save( s2 );
      session.save( car );
      session.getTransaction().rollback();
      session.close();
      System.out.println( "testOneToManyMapping ends ......." );

   }

   @Test
   public void testManyToOneMapping()
   {
      System.out.println( "testManyToOneMapping begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company("Auto repaire 1");
      Service serv1 = new Service();
      Service serv2 = new Service();

      serv1.setCompany( comp );
      serv2.setCompany( comp );

      session.save( comp );
      session.save( serv1 );
      session.save( serv2 );
      session.getTransaction().rollback();
      session.close();
      System.out.println( "testManyToOneMapping ends ......." );

   }
   
   @Test
   public void testPersistentObject()
   {
      System.out.println( "testPersistentObject begins ......." );
      Company comp = new Company( "CAI" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      System.out.println( "Savin the object with state " + comp );
      session.save( comp );
      System.out.println( "Saved the object and now updating using setter method" );
      comp.setName( "updated the name" );
      System.out.println( "The upadted object after saving is " + comp );
      session.getTransaction().commit();
      session.close();
      session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp2 = session.get( Company.class, 1L );
      System.out.println( "Retrieved object is " + comp2 );
      assertEquals( comp.getName(), comp2.getName() );
      System.out.println( "testPersistentObject ends ......." );

   }

   @Test
   public void testDetachedObject()
   {
      System.out.println( "testDetachedObject begins ......." );
      Company comp = new Company( "Detached company" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      System.out.println( "Savin the object with state " + comp );
      session.save( comp );
      session.getTransaction().commit();
      session.close();
      System.out.println( "Saved the object and closed the session and now updating using setter method" );
      comp.setName( "updated company name" );
      System.out.println( "The upadted object after saving and closing the session is " + comp );
      session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp2 = session.get( Company.class, 1L );
      System.out.println( "Retrieved object is " + comp2 );
      assertNotEquals( comp.getName(), comp2.getName() );
      System.out.println( "testDetachedObject ends ......." );

   }

   @Test
   public void testTransientObject()
   {
      System.out.println( "testTransientObject begins ......" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Company comp = session.get( Company.class, 1L );
      System.out.println( "Retrieved person from DB is " + comp );
      session.delete( comp );
      session.getTransaction().commit();
      System.out.println( "Deleted the person, so retrieve one more time to confirm. After deleteing the object becomes transient" );
      comp.setName( "Deleted Object name" );
      Company comp2 = session.get( Company.class, 1L );
      System.out.println( "Retrieved person from DB after deletion is " + comp2 );
      session.close();
      assertEquals( "Deleted Object name", comp.getName() );
      assertNull( comp2 );
      System.out.println( "testTransientObject ends ......." );

   }

   @Test
   public void testNamedQueries()
   {
      System.out.println( "testNamedQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company( "samsung" );
      Company comp2 = new Company( "apple" );
      session.save( comp );
      session.save( comp2 );
      session.getTransaction().commit();
      Query query = session.getNamedQuery( "Company.byName" );
      query.setString( 0, "apple" );
      List<Company> list = (List<Company>) query.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testNamedQueries ends ......." );

   }

   @Test
   public void testNamedNativeQueries()
   {
      System.out.println( "testNamedNativeQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company( "samsung" );
      Company comp2 = new Company( "apple" );
      session.save( comp );
      session.save( comp2 );
      session.getTransaction().commit();
      Query query = session.getNamedQuery( "Company.byId" );
      query.setInteger( "id", 1 );
      List<Company> list = (List<Company>) query.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testNamedNativeQueries ends ......." );

   }

   @Test
   public void testCriteria()
   {
      System.out.println( "testNamedNativeQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company( "samsung" );
      Company comp2 = new Company( "apple" );
      session.save( comp );
      session.save( comp2 );
      session.getTransaction().commit();

      Criteria criteria = session.createCriteria( Company.class );
      criteria.add( Restrictions.or( Restrictions.eq( "name", "apple" ), Restrictions.between( "id", 1, 5 ) ) );

      List<Company> list = (List<Company>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 2, list.size() );
      session.close();
      System.out.println( "testNamedNativeQueries ends ......." );

   }

   @Test
   public void testProjections()
   {
      System.out.println( "testProjections begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company( "samsung" );
      Company comp2 = new Company( "apple" );
      session.save( comp );
      session.save( comp2 );
      session.getTransaction().commit();

      Criteria criteria = session.createCriteria( Company.class );
      criteria.setProjection( Projections.property( "id" ) ).addOrder( Order.desc( "id" ) );

      List<String> list = (List<String>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 2, list.size() );
      session.close();
      System.out.println( "testProjections ends ......." );

   }

   @Test
   public void testExample()
   {
      System.out.println( "testExample begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Company comp = new Company( "samsung" );
      Company comp2 = new Company( "apple" );
      session.save( comp );
      session.save( comp2 );
      session.getTransaction().commit();

      Company comp3 = new Company( "apple" );

      Example ex = Example.create( comp3 );
      Criteria criteria = session.createCriteria( Company.class );
      criteria.add( ex );
      List<Company> list = (List<Company>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testExample ends ......." );

   }

}
