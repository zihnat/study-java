package net.lessons.cassandra;
import com.datastax.driver.core.Session;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    KeyspaceRepository schemaRepository;
    CarRepository carRepository;
    String KEYSPACE_NAME = "my_keyspace01";
    Connector client = null;

    try{
      //connect to cassandra
      client = new Connector();
      client.connect("127.0.0.1", 9042);
      Session session = client.getSession();
      //create namespace and table if not exist
      schemaRepository = new KeyspaceRepository(session);
      schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
      schemaRepository.useKeyspace(KEYSPACE_NAME);
      carRepository = new CarRepository(session);
      carRepository.createTable();
      //insert million cars to table
      System.out.println("Inserting million cars time: " + Tester.getInsertTime(carRepository, 1000000) + " milliseconds");

      //select by id million cars in table
      System.out.println("Search by id million cars time: " + Tester.getSercheTime(carRepository, 1000000) + " milliseconds");

      //delete million cars from table
      System.out.println("Delete by id million cars time: " + Tester.getDeleteTime(carRepository, 1000000) + " milliseconds");

    }catch(Exception e){
      System.out.println("Exception " + e);
    }finally{
      try{
        if(client != null){
          client.close();
        }
      }catch(Exception e){
        System.out.println("Exception while closing CarDAO in finally " + e);
      }
    }
  }

  private static void showList(List<?> list){
    for(int i = 0; i < list.size(); i++){
      System.out.println(list.get(i));
    }
  }

}
