package net.lessons.dao;
import java.util.*;
import net.lessons.dao.CarDAO;
import net.lessons.dao.CarDTO;

public class Main {

  public static void main(String[] args) {
    CarDAO dao = null;
    try{
      dao = new CarDAO();
      List <CarDTO> list = dao.getAllCars();
      showList(list);
      int id = dao.addCar("BMV", "A7");
      System.out.println("New record id is " + id);
      list = dao.getAllCars();
      showList(list);
    }catch(Exception e){
      System.out.println("Exception " + e);
    }finally{
      try{
        if(dao != null){
          dao.close();
        }
      }catch(Exception e){
        System.out.println("Exception in finally " + e);
      }
    }
  }

  private static void showList(List<CarDTO> list){
    for(int i = 0; i < list.size(); i++){
      System.out.println(list.get(i));
    }
  }
}
