package net.lessons.dao;
import java.util.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args){
        CompanyDAO daoComp = null;
        ServiceDAO daoServ = null;
        DAO dao = null;

        try{
          AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);
            //*/
            daoComp = context.getBean(CompanyDAO.class);
            daoComp.openSession();
            List <Company> listComps = daoComp.getAll();
            showList(listComps);
            daoComp.closeSession();
            //*/
            daoServ = context.getBean(ServiceDAO.class);
            daoServ.openSession();
            List<Service> listServs = daoServ.getAll();
            showList(listServs);
            daoServ.closeSession();
            //*/
            dao = context.getBean(CarDAO.class);
            dao.openSession();
            List<Car> listCars = dao.getAll();
            showList(listCars);
            dao.closeSession();
            //*/
        }catch(Exception e){
            System.out.println("Exception " + e);
            e.printStackTrace();
        }finally{
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
                System.out.println("Exception while closing ServiceDAO in finally " + e);
            }
            try{
                if(dao != null){
                    dao.close();
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
