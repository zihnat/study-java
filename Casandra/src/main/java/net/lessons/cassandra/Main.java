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
      //insert some cars to table
      Car car = new Car(0, "VW", "Bug");
      carRepository.insertCar(car);
      car = new Car(1, "BMW", "A7");
      carRepository.insertCar(car);
      car = new Car(2, "Audi", "TT");
      carRepository.insertCar(car);
      //select all cars
      System.out.println("Must be 3 results:");
      List<Car> cars = carRepository.selectAll();
      for(int i = 0; i < cars.size(); i++){
        System.out.println(cars.get(i));
      }
      // update one row and delete another
      System.out.println("Must be 2 results:");
      car = new Car(1, "Lada", "Kalina");
      carRepository.insertCar(car);
      carRepository.deleteCar(0);
      cars = carRepository.selectAll();
      for(int i = 0; i < cars.size(); i++){
        System.out.println(cars.get(i));
      }
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
