package net.lessons.cassandra;
import java.util.*;

class Tester{

  public static long getInsertTime(CarRepository repo, int maxId){
    long start   = System.currentTimeMillis();
    for(int i = 0; i < maxId; i++){
      Car car = new Car(i, "VW", "Bug");
      repo.insertCar(car);
    }
    long finish  = System.currentTimeMillis();
    return finish - start;
  }

  public static long getSercheTime(CarRepository repo, int maxId){
    long start   = System.currentTimeMillis();
    for(int i = 0; i < maxId; i++){
      repo.selectById(i);
    }
    long finish  = System.currentTimeMillis();
    return finish - start;
  }

  public static long getDeleteTime(CarRepository repo, int maxId){
    long start   = System.currentTimeMillis();
    for(int i =0; i < maxId; i++){
      repo.deleteCar(i);
    }
    long finish  = System.currentTimeMillis();
    return finish - start;
  }

}
