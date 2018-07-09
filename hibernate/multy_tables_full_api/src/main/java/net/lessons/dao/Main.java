package net.lessons.dao;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    DAO daoCar = null;
    DAO daoComp = null;
    DAO daoServ = null;
    try{
      DAOFactory factory = new DAOFactory();
      daoCar = factory.getDAO(Car.class);
      List <Car> listCars = daoCar.getAll();
      showList(listCars);
      daoComp = factory.getDAO(Company.class);
      List <Company> listComps = daoComp.getAll();
      showList(listComps);
      System.out.println(" - Auto services at work - ");
      daoServ = factory.getDAO(Service.class);
      List <Service> listServs = daoServ.getAll();
      showList(listServs);
    }catch(Exception e){
      System.out.println("Exception " + e);
      e.printStackTrace();
    }finally{
      try{
        if(daoCar != null){
          daoCar.close();
        }
      }catch(Exception e){
        System.out.println("Exception while closing CarDAO in finally " + e);
        e.printStackTrace();
      }
      try{
          if(daoComp != null){
          daoComp.close();
        }
      }catch(Exception e){
        System.out.println("Exception while closing CompanyDAO in finally " + e);
      }
      try{
          if(daoServ != null){
          daoServ.close();
        }
      }catch(Exception e){
        System.out.println("Exception while closing CompanyDAO in finally " + e);
      }
    }
  }

  private static void showList(List<?> list){
    for(int i = 0; i < list.size(); i++){
      System.out.println(list.get(i));
    }
  }

}
