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
    
  private List<String> headCar = new ArrayList<String>();
  private List<String> headCompany = new ArrayList<String>();
  private List<String> headService = new ArrayList<String>();

  /**
   * this life-cycle method is invoked when this servlet is first accessed
   * by the client
   */
  public void init(ServletConfig config) {
    System.out.println("Servlet is being initialized");
    
    headCar.add("id");
    headCar.add("mark");
    headCar.add("model");
    
    headCompany.add("id");
    headCompany.add("name");
    
    headService.add("id");
    headService.add("date");
    headService.add("price");
    headService.add("car id");
    headService.add("company id");
  }

  /**
   * handles HTTP GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer    = response.getWriter();
//    Connection connection = null ;
//    Statement statement   = null;

    InterfaceDAO daoCar = null;
    InterfaceDAO daoComp = null;
    InterfaceDAO daoServ = null;
    try{
      DAOFactory factory = new DAOFactory();
      
      String htmlHeader  = getHeader("Servlet With Data");
      writer.println(htmlHeader);
      
      //table cars
      daoCar = factory.getDAO(CarDTO.class);
      String tableHeader = getTableHeader(headCar);
      writer.println(tableHeader);
      String tableRows = fealTableByCars(daoCar.getAll());
      writer.println(tableRows);
      String tableFooter = getTableFooter();
      writer.println(tableFooter);
      
      //table company
      daoComp = factory.getDAO(CompanyDTO.class);
      tableHeader = getTableHeader(headCompany);
      writer.println(tableHeader);
      tableRows = fealTableByComp(daoComp.getAll());
      writer.println(tableRows);
      tableFooter = getTableFooter();
      writer.println(tableFooter);
      
      //table service
      daoServ = factory.getDAO(ServiceDTO.class);
      tableHeader = getTableHeader(headService);
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
  private String getTableHeader(List<String> head){
    String tableHeader = null;
    tableHeader = "<TABLE border=1>";
    String tableHead   = getTableHead(head);
    return tableHeader + tableHead;
  }
  private String getTableFooter(){
    String tableHeader = "</TABLE>";
    return tableHeader;
  }
  //teble head
  private String getTableHead(List<String> head){
    int vsize = head.size();
    String contents = new String();
    contents = "<tr>";
    for(int i = 0; i < vsize; i++){
      String value = head.get(i).toString();
      contents += "<th>" + value +"</th>";
    }
    contents += "</tr>\n";
    return contents;
  }
/*  private String getTableRow(Vector<String> head, ResultSet rs) throws SQLException{
    int vsize = head.size();
    String contents = new String();
    try{
      while(rs.next()){
        contents += "<tr>";
        for(int i = 0; i < vsize; i++){
          String value = head.elementAt(i).toString();
          contents += "<th>" + rs.getString(value) +"</th>";
        }
        contents +=  "</tr>\n";
      }
    }catch (SQLException e){
      throw e;
    }
    return contents;
  }*/
  // table content
  private String fealTableByCars(List<CarDTO> rows){
      String contents = new String();
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
