package net.lessons.dao;
import java.util.*;
//import net.lessons.dao.CarDAO;
//import net.lessons.dao.CarDTO;

public class Main {

  public static void main(String[] args) {
    InterfaceDAO daoCar = null;
    InterfaceDAO daoComp = null;
    InterfaceDAO daoServ = null;
    try{
      DAOFactory factory = new DAOFactory();
      //daoCar = new CarDAO();
      daoCar = factory.getDAO(CarDTO.class);
      //daoCar.update(new CarDTO(8,"Lada","Kalina"));
      List <CarDTO> listCars = daoCar.getAll();
      showList(listCars);
      daoComp = factory.getDAO(CompanyDTO.class);
      List <CompanyDTO> listComps = daoComp.getAll();
      showList(listComps);
      System.out.println(" - Auto services at work - ");
      daoServ = factory.getDAO(ServiceDTO.class);
      List <ServiceDTO> listServs = daoServ.getAll();
      showList(listServs);
    }catch(Exception e){
      System.out.println("Exception " + e);
    }finally{
      try{
        if(daoCar != null){
          daoCar.close();
        }
      }catch(Exception e){
        System.out.println("Exception while closing CarDAO in finally " + e);
      }
      try{
          if(daoComp != null){
          daoComp.close();
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
