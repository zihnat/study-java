package net.lessons.servlet;

import java.io.IOException;
import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.sql.Date;
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
  throws IOException {
    String action = request.getParameter("action");
    String objType = request.getParameter("object");
    
    String idStr = null;
    int id = 0;
    Boolean doget = true;
    try{
        switch(action){
            case "createrequest":
                doget = false;
                printCreateForm(request, response);
                break;
            case "create":
                switch(objType){
                    case "car":
                        String mark = request.getParameter("mark");
                        String model = request.getParameter("model");
                        daoCar.add(new CarDTO(mark, model));
                        break;
                    case "company":
                        String name = request.getParameter("name");
                        daoComp.add(new CompanyDTO(name));
                        break;
                    case "service":
                        String dateStr = request.getParameter("date");
                        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = java.sql.Date.valueOf(dateStr);
                        String priceStr = request.getParameter("price");
                        Float price = Float.parseFloat(priceStr);
                        String carStr = request.getParameter("carid");
                        int car = Integer.parseInt(carStr);
                        String compStr = request.getParameter("companyid");
                        int comp = Integer.parseInt(compStr);
                        daoServ.add(new ServiceDTO(date, price, car, comp));
                        break;
                }
                break;
            case "delete":
                idStr = request.getParameter("id");
                id = Integer.parseInt(idStr);
                try{
                    switch(objType){
                        case "car":
                            daoCar.delete(id);
                            break;
                        case "company":
                            daoComp.delete(id);
                            break;
                        case "service":
                            daoServ.delete(id);
                            break;
                    }
                }catch(DAOException e){
                    System.out.println(e);
                }
                break;
            case "update":
                idStr = request.getParameter("id");
                id = Integer.parseInt(idStr);
                switch(objType){
                    case "car":
                        String mark = request.getParameter("mark");
                        String model = request.getParameter("model");
                        daoCar.update(new CarDTO(id, mark, model));
                        break;
                    case "company":
                        String name = request.getParameter("name");
                        daoComp.update(new CompanyDTO(id, name));
                        break;
                    case "service":
                        String dateStr = request.getParameter("date");
                        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = java.sql.Date.valueOf(dateStr);
                        String priceStr = request.getParameter("price");
                        Float price = Float.parseFloat(priceStr);
                        String carStr = request.getParameter("carid");
                        int car = Integer.parseInt(carStr);
                        String compStr = request.getParameter("companyid");
                        int comp = Integer.parseInt(compStr);
                        daoServ.update(new ServiceDTO(id, date, price, car, comp));
                        break;
                }
                break;
            case "updaterequest":
                doget = false;
                printUpdateForm(request, response);
                break;
            default:
                break;
        }
    }catch(Exception e){
        System.out.println(e);
    }finally{
        if(doget){
            doGet(request, response);
        }
    }
  }

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
      contents = "<tr><th>id</th><th>mark</th><th>model</th>" +
              "<th>" + getCreateButton("car") + "</th></tr>\n";
      for(int i = 0; i<rows.size(); i++){
        CarDTO car = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + car.getId()+"</td>";
        contents += "<td>" + car.getMark()+"</td>";
        contents += "<td>" + car.getModel()+"</td>";
        contents += "<td>" + getUpdateButton("car", car.getId()) +"</td>";
        contents += "<td>" + getDeleteButton("car", car.getId()) +"</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
  
  private String fealTableByComp(List<CompanyDTO> rows){
      String contents = new String();
      contents = "<tr><th>id</th><th>name</th>\n" +
              "<th>" + getCreateButton("company") + "</th></tr>\n";
      for(int i = 0; i<rows.size(); i++){
        CompanyDTO comp = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + comp.getId()+"</td>";
        contents += "<td>" + comp.getName()+"</td>";
        contents += "<td>" + getUpdateButton("company", comp.getId()) + "</td>";
        contents += "<td>" + getDeleteButton("company", comp.getId()) + "</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
  
  private String fealTableByServ(List<ServiceDTO> rows){
      String contents = new String();
      contents = "<tr><th>id</th><th>date</th><th>price</th><th>car id</th><th>company id</th>" +
              "<th>" + getCreateButton("service") + "</th></tr>\n";
      for(int i = 0; i<rows.size(); i++){
        ServiceDTO serv = rows.get(i);
        contents += "<tr>";
        contents += "<td>" + serv.getId()+"</td>";
        contents += "<td>" + serv.getDate()+"</td>";
        contents += "<td>" + serv.getPrice()+"</td>";
        contents += "<td>" + serv.getCar()+"</td>";
        contents += "<td>" + serv.getCompany()+"</td>";
        contents += "<td>" + getUpdateButton("service", serv.getId()) + "</td>";
        contents += "<td>" + getDeleteButton("service", serv.getId()) + "</td>";
        contents +=  "</tr>\n";
      }
      return contents;
  }
  
  private String getCreateButton(String type){
      String contents = "<form action=\"./\" method=\"post\">" +
              "<input type=\"hidden\" name=\"action\" value=\"createrequest\"/>\n" +
              "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
              "<input type=\"submit\" value=\"Create\" />\n" +
              "</form>";
      return contents;
  }
  
  private String getUpdateButton(String type, int id){
      String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"updaterequest\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
            "<input type=\"submit\" value=\"Update\" />\n" +
            "</form>";
      return contents;
  }
  
  private String getDeleteButton(String type, int id){
      String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"delete\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
            "<input type=\"submit\" value=\"Delete\" />\n" +
            "</form>";
      return contents;
  }
  
  private void printCreateForm(HttpServletRequest request, HttpServletResponse response)
  throws IOException, DAOException{
    String objType = request.getParameter("object");
    
    String htmlHeader = null;
    String htmlFooter = null;
    PrintWriter writer = null;
    try{
        writer = response.getWriter();
        htmlHeader  = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        writer.println("Hello! You send create request for object type " + objType + ". ");
        String contents = "<form action=\"./\" method=\"post\">\n" +
                "<input type=\"hidden\" name=\"action\" value=\"create\"/>\n" +
                            //"<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
                "<input type=\"hidden\" name=\"object\" value=\"" + objType + "\"/>\n";
        switch(objType){
            case "car":
                contents += "New Car " +
                    " mark: <input type=\"text\" name=\"mark\" size=\"30\" placeholder=\"mark\"/>\n" +
                    " model: <input type=\"text\" name=\"model\" size=\"30\" placeholder=\"model\"/>\n";
                break;
            case "company":
                contents += "New Company " +
                    " name: <input type=\"text\" name=\"name\" size=\"50\" placeholder=\"Company name\"/>\n";
                break;
            case "service":
                    contents += "New service record " +
                        " date: <input type=\"text\" name=\"date\" size=\"10\" placeholder=\"YYYY-MM-DD\"/>\n" +
                        " price: <input type=\"number\" name=\"price\" size=\"5\" placeholder=\"n.nn\" step=\"any\"/>\n" +
                        " car id : " +
                        " <select name=\"carid\">\n";
                    List<CarDTO> cars = daoCar.getAll();
                    for(int i = 0; i < cars.size(); i++){
                        CarDTO car = cars.get(i);
                        contents += "    <option value=\"" + car.getId() + "\">" + car.toString() + "</option>\n";
                    }
                    contents += " </select>";
                    contents += " company id: <select name=\"companyid\"/>\n";
                    List<CompanyDTO> comps = daoComp.getAll();
                    for(int i = 0; i < comps.size(); i++){
                        CompanyDTO comp = comps.get(i);
                        contents += "    <option value=\"" + comp.getId() + "\">" + comp.toString() + "</option>\n";
                    }
                    contents += " </select>";
                    break;
            default:
                contents += "Error: type \"" + objType + "\" does not exist!";
        }
        contents += "<input type=\"submit\" value=\"Submit\" />\n" +
            "</form>";
        writer.println(contents);
        contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"cancel\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + objType + "\"/>\n" +
            "<input type=\"submit\" value=\"Cancel\" />\n" +
            "</form>";
        writer.println(contents);
        htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(DAOException e){
        System.out.println(e);
    }finally{
        if(writer != null){
            writer.flush();
            writer.close();
        } 
    }
  }
  
  private void printUpdateForm(HttpServletRequest request, HttpServletResponse response) 
  throws IOException {
    String action = request.getParameter("action");
    String objType = request.getParameter("object");
    
    String idStr = null;
    int id = 0;
    Boolean doget = true;
    String htmlHeader = null;
    String htmlFooter = null;
    PrintWriter writer = null;
    try{
        idStr = request.getParameter("id");
        id = Integer.parseInt(idStr);
        writer = response.getWriter();
        htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        writer.println("Hello! You send update request for object type " + objType + ". ");
        String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"update\"/>\n" +
            "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + objType + "\"/>\n";
        switch(objType){
            case "car":
                CarDTO car = (CarDTO) daoCar.getById(id);
                contents += "Car with id \"" + id + "\" " +
                    " mark: <input type=\"text\" name=\"mark\" size=\"30\" placeholder=\"mark\" value=\"" + car.getMark() + "\"/>\n" +
                    " model: <input type=\"text\" name=\"model\" size=\"30\" placeholder=\"model\" value=\"" + car.getModel() + "\"/>\n";
                break;
            case "company":
                CompanyDTO comp = (CompanyDTO) daoComp.getById(id);
                contents += "Company with id \"" + id +"\"" +
                    " name: <input type=\"text\" name=\"name\" size=\"50\" placeholder=\"Company name\" value=\"" + comp.getName() + "\"/>\n";
                break;
            case "service":
                ServiceDTO serv = (ServiceDTO) daoServ.getById(id);
                contents += "New service record " +
                    " date: <input type=\"text\" name=\"date\" size=\"10\" placeholder=\"YYYY-MM-DD\" value=\"" + serv.getDate() + "\"/>\n" +
                    " price: <input type=\"number\" name=\"price\" size=\"5\" placeholder=\"n.nn\" value=\"" + serv.getPrice() + "\" step=\"any\"/>\n" +
                    " car id : " +
                    " <select name=\"carid\">\n";
                List<CarDTO> cars = daoCar.getAll();
                for(int i = 0; i < cars.size(); i++){
                    CarDTO car1 = cars.get(i);
                    contents += "    <option ";
                    if(serv.getCar() == car1.getId()){
                        contents += "selected=\"selected\" ";
                    }
                    contents += "value=\"" + car1.getId() + "\">" + car1.toString() + "</option>\n";
                }
                contents += " </select>";
                contents += " company id: <select name=\"companyid\"/>\n";
                List<CompanyDTO> comps = daoComp.getAll();
                for(int i = 0; i < comps.size(); i++){
                    CompanyDTO comp1 = comps.get(i);
                    contents += "    <option ";
                    if(serv.getCompany() == comp1.getId()){
                        contents += "selected=\"selected\" ";
                    }
                    contents += "value=\"" + comp1.getId() + "\">" + comp1.toString() + "</option>\n";
                }
                contents += " </select>";
                break;
            default:
                contents += "Error: type \"" + objType + "\" does not exist!";
        }
        contents += "<input type=\"submit\" value=\"Submit\" />\n" +
            "</form>";
        writer.println(contents);
        contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"cancel\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + objType + "\"/>\n" +
            "<input type=\"submit\" value=\"Cancel\" />\n" +
            "</form>";
        writer.println(contents);
        htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(DAOException e){
        System.out.println(e);
    }finally{
        if(writer != null){
            writer.flush();
            writer.close();
        } 
    }
  }
}
