package net.lessons.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lessons.dao.*;

public class ServletWithData extends HttpServlet {
    
    private InterfaceDAO daoCar = null;
    private InterfaceDAO daoComp = null;
    private InterfaceDAO daoServ = null;

  /**
   * this life-cycle method is invoked when this servlet is first accessed
   * by the client
   */
  public void init(ServletConfig config){
    System.out.println("Servlet is being initialized");
    try{
        DAOFactory factory = new DAOFactory();
        daoCar = factory.getDAO(CarDTO.class);
        daoComp = factory.getDAO(CompanyDTO.class);
        daoServ = factory.getDAO(ServiceDTO.class);
    }catch(DAOException e){
        System.out.println(e);
    }
  }

  /**
   * handles HTTP GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer    = response.getWriter();
//    Connection connection = null ;
//    Statement statement   = null;

    try{
      String htmlHeader  = getHeader("Servlet With Data");
      writer.println(htmlHeader);
      
      //table cars
      String tableHeader = getTableHeader();
      writer.println(tableHeader);
      String tableRows = fealTableByCars(daoCar.getAll());
      writer.println(tableRows);
      String tableFooter = getTableFooter();
      writer.println(tableFooter);
      
      //table company
      tableHeader = getTableHeader();
      writer.println(tableHeader);
      tableRows = fealTableByComp(daoComp.getAll());
      writer.println(tableRows);
      tableFooter = getTableFooter();
      writer.println(tableFooter);
      
      //table service
      tableHeader = getTableHeader();
      writer.println(tableHeader);
      tableRows = fealTableByServ(daoServ.getAll());
      writer.println(tableRows);
      tableFooter = getTableFooter();
      writer.println(tableFooter);
      
      String htmlFooter  = getFooter();
      writer.println(htmlFooter);

      //writer.println("<html>Hello from Simple Java servlet!</html>");

    } catch (Exception e) {
      writer.println("An error occured while retrieving data: "+ e.toString());
    }finally {
      writer.flush();
    }
    writer.close();
  }

  /**
   * handles HTTP POST request
   */
  /*public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    String paramName = request.getParameter("name");
    String paramAge = request.getParameter("age");
    int age = Integer.parseInt(paramAge);
    PrintWriter writer = response.getWriter();
    writer.println("<html>Hello " + paramName + " in age of " + age + "</html>");
    writer.flush();
  }*/

  /**
   * this life-cycle method is invoked when the application or the server
   * is shutting down
   */
  public void destroy() {
    try{
        if(daoCar != null){
            daoCar.close();
        }
    }catch(DAOException e){
        System.out.println(e);
    }
    try{
        if(daoComp != null){
            daoComp.close();
        }
    }catch(DAOException e){
        System.out.println(e);
    }
    try{
        if(daoServ != null){
            daoServ.close();
        }
    }catch(DAOException e){
        System.out.println(e);
    }
    System.out.println("Servlet is being destroyed");
  }

  //create web page header and footer
  private String getHeader(String title){
    String htmlHeader = null;
    htmlHeader = "<HTML><HEAD><TITLE> " + title + " </TITLE></HEAD><BODY>";
    return htmlHeader;
  }
  private String getFooter(){
    String htmlFooter = "</BODY></HTML>";
    return htmlFooter;
  }
  //create web table
  private String getTableHeader(){
    String tableHeader = null;
    tableHeader = "<TABLE border=1>";
    return tableHeader;
  }
  private String getTableFooter(){
    String tableHeader = "</TABLE>";
    return tableHeader;
  }

  
  // table content
  private String fealTableByCars(List<CarDTO> rows){
      String contents = new String();
      contents = "<tr><th>id</th><th>mark</th><th>model</th></tr>\n";
      for(int i = 0; i<rows.size(); i++){
        CarDTO car = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + car.getId()+"</td>";
        contents += "<td>" + car.getMark()+"</td>";
        contents += "<td>" + car.getModel()+"</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
  
  private String fealTableByComp(List<CompanyDTO> rows){
      String contents = new String();
      contents = "<tr><th>id</th><th>name</th></tr>\n";
      for(int i = 0; i<rows.size(); i++){
        CompanyDTO comp = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + comp.getId()+"</td>";
        contents += "<td>" + comp.getName()+"</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
  
  private String fealTableByServ(List<ServiceDTO> rows){
      String contents = new String();
      contents = "<tr><th>id</th><th>date</th><th>price</th><th>car id</th><th>company id</th></tr>";
      for(int i = 0; i<rows.size(); i++){
        ServiceDTO serv = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + serv.getId()+"</td>";
        contents += "<td>" + serv.getDate()+"</td>";
        contents += "<td>" + serv.getPrice()+"</td>";
        contents += "<td>" + serv.getCar()+"</td>";
        contents += "<td>" + serv.getCompany()+"</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
}
