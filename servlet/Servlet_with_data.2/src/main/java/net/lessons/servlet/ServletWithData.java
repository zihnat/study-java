package net.lessons.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    processRequest(request, response);
  }

  /**
   * handles HTTP POST request
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
  throws IOException{
    try{
        String action = "";
        if (request.getParameterMap().containsKey("action")) {
          action = request.getParameter("action");
        }
        switch(action){
            case "createrequestcar":
                printCreateCarForm(request, response);
                break;
            case "createrequestcompany":
                printCreateCompanyForm(request, response);
                break;
            case "createrequestservice":
                printCreateServiceForm(request, response);
                break;
            case "createcar":
                createCar(request);
                printMainPage(request, response);
                break;
            case "createcompany":
                createCompany(request);
                printMainPage(request, response);
                break;
            case "createservice":
                createService(request);
                printMainPage(request, response);
                break;
            case "deletecar":
                daoCar.delete(Integer.parseInt(request.getParameter("id")));
                printMainPage(request, response);
                break;
            case "deletecompany":
                daoComp.delete(Integer.parseInt(request.getParameter("id")));
                printMainPage(request, response);
                break;
            case "deleteservice":
                daoServ.delete(Integer.parseInt(request.getParameter("id")));
                printMainPage(request, response);
                break;
            case "updatecar":
                updateCar(request);
                printMainPage(request, response);
                break;
            case "updatecompany":
                updateCompany(request);
                printMainPage(request, response);
                break;
            case "updateservice":
                updateService(request);
                printMainPage(request, response);
                break;
            case "updaterequestcar":
                printUpdateCarForm(request, response);
                break;
            case "updaterequestcompany":
                printUpdateCompanyForm(request, response);
                break;
            case "updaterequestservice":
                printUpdateServiceForm(request, response);
                break;
            default:
                printMainPage(request, response);
                break;
        }
    }catch(NullPointerException e){
        String text = null;
        if(daoCar == null || daoComp == null || daoServ == null){
            text = "One or more DAO objects does not exist. Probably some problems with DB connection.";
        }else{
            text = "Error occured: " + e;
        }
        PrintWriter writer = response.getWriter();
        writer.println(text);
    }catch(Exception e){
        PrintWriter writer = response.getWriter();
        writer.println("Error occured: " + e);
    }finally{
        PrintWriter writer = response.getWriter();
        writer.flush();
        writer.close();
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
              "<input type=\"hidden\" name=\"action\" value=\"createrequest" + type + "\"/>\n" +
              "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
              "<input type=\"submit\" value=\"Create\" />\n" +
              "</form>";
      return contents;
  }

  private String getUpdateButton(String type, int id){
      String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"updaterequest" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
            "<input type=\"submit\" value=\"Update\" />\n" +
            "</form>";
      return contents;
  }

  private String getDeleteButton(String type, int id){
      String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"delete" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"object\" value=\"" + type + "\"/>\n" +
            "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n" +
            "<input type=\"submit\" value=\"Delete\" />\n" +
            "</form>";
      return contents;
  }

  private void printMainPage(HttpServletRequest request, HttpServletResponse response)
  throws Exception{
    try{
      PrintWriter writer = response.getWriter();
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
    }catch(NullPointerException e){
        String text = null;
        if(daoCar == null || daoComp == null || daoServ == null){
            text = "One or more DAO objects does not exist. Probably some problems with DB connection.";
        }else{
            text = "Error occured: " + e;
        }
        PrintWriter writer = response.getWriter();
        writer.println(text);
    } catch (Exception e) {
        PrintWriter writer = response.getWriter();
        writer.println("An error occured while retrieving data: " + e.toString());
    }
  }

  //create objects

  private void createCar(HttpServletRequest request)
  throws DAOException, ParseException{
      String mark = request.getParameter("mark");
      String model = request.getParameter("model");
      daoCar.add(new CarDTO(mark, model));
  }

  private void createCompany(HttpServletRequest request)
  throws DAOException, ParseException{
      String name = request.getParameter("name");
      daoComp.add(new CompanyDTO(name));
  }

  private void createService(HttpServletRequest request)
  throws DAOException, ParseException{
      String dateStr = request.getParameter("date");
      String priceStr = request.getParameter("price");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      java.util.Date theDate;
      theDate = format.parse(dateStr);
      Date date = new java.sql.Date(theDate.getTime());
      Float price = Float.parseFloat(priceStr);
      String carStr = request.getParameter("carid");
      int car = Integer.parseInt(carStr);
      String compStr = request.getParameter("companyid");
      int comp = Integer.parseInt(compStr);
      daoServ.add(new ServiceDTO(date, price, car, comp));
  }

  // update objects

  private void updateCar(HttpServletRequest request)
  throws DAOException, ParseException{
    int id = Integer.parseInt(request.getParameter("id"));
    String mark = request.getParameter("mark");
    String model = request.getParameter("model");
    daoCar.update(new CarDTO(id, mark, model));
  }

  private void updateCompany(HttpServletRequest request)
  throws DAOException, ParseException{
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    daoComp.update(new CompanyDTO(id, name));
  }

  private void updateService(HttpServletRequest request)
  throws DAOException, ParseException{
    int id = Integer.parseInt(request.getParameter("id"));
    String dateStr = request.getParameter("date");
    String priceStr = request.getParameter("price");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date theDate;
    theDate = format.parse(dateStr);
    Date date = new java.sql.Date(theDate.getTime());
    Float price = Float.parseFloat(priceStr);
    String carStr = request.getParameter("carid");
    int car = Integer.parseInt(carStr);
    String compStr = request.getParameter("companyid");
    int comp = Integer.parseInt(compStr);
    daoServ.update(new ServiceDTO(id, date, price, car, comp));
  }

  // print create and update forms

  private String getFormHeader(String action, String objType){
      String message = "You send " + action + " request for object type " + objType + ".<br>";
      String formHead = "<form action=\"./\" method=\"post\">\n" +
          "<input type=\"hidden\" name=\"action\" value=\"" + action + objType + "\"/>\n";
      return message + formHead;
  }

  private String getFormFooter(){
    return "<input type=\"submit\" value=\"Submit\" /></form>";
  }

  private String getCancelForm(){
    String contents = "<form action=\"./\" method=\"post\">\n" +
        "<input type=\"hidden\" name=\"action\" value=\"cancel\"/>\n" +
        "<input type=\"submit\" value=\"Cancel\" />\n" +
        "</form>";
    return contents;
  }

  private void printCreateCarForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        //int id = 0;

        String contents = getFormHeader("create", "car");
        contents += "New Car ";
        contents += " mark: <input type=\"text\" name=\"mark\" size=\"30\" placeholder=\"mark\"/>\n" +
                    " model: <input type=\"text\" name=\"model\" size=\"30\" placeholder=\"model\"/>\n";

        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

  private void printUpdateCarForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        int id = Integer.parseInt(request.getParameter("id"));

        String contents = getFormHeader("update", "car");
        contents += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n";
        CarDTO car = (CarDTO) daoCar.getById(id);
        String mark = car.getMark();
        String model = car.getModel();
        contents += "Car with id \"" + id + "\" ";
        contents += " mark: <input type=\"text\" name=\"mark\" size=\"30\" placeholder=\"mark\" value=\"" + mark + "\"/>\n" +
                    " model: <input type=\"text\" name=\"model\" size=\"30\" placeholder=\"model\" value=\"" + model + "\"/>\n";

        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

  private void printCreateCompanyForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        //int id = 0;

        String contents = getFormHeader("create", "company");

        contents += "New Company ";
        contents += " name: <input type=\"text\" name=\"name\" size=\"50\" placeholder=\"Company name\"/>\n";

        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

  private void printUpdateCompanyForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);

        int id = Integer.parseInt(request.getParameter("id"));
        String contents = getFormHeader("update", "company");

        contents += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n";
        CompanyDTO comp = (CompanyDTO) daoComp.getById(id);
        String name = comp.getName();
        contents += "Company with id \"" + id +"\"";
        contents += " name: <input type=\"text\" name=\"name\" size=\"50\" placeholder=\"Company name\" value=\"" + name + "\"/>\n";

        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

  private void printCreateServiceForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        //int id = 0;

        String contents = getFormHeader("create", "service");

        contents += "New service record ";
        contents += " date: <input type=\"text\" name=\"date\" size=\"10\" placeholder=\"YYYY-MM-DD\"/>\n";
        contents += " price: <input type=\"number\" name=\"price\" size=\"5\" placeholder=\"n.nn\" step=\"any\"/>\n";
        contents += " car id : <select name=\"carid\">\n";
        List<CarDTO> cars = daoCar.getAll();
        for(int i = 0; i < cars.size(); i++){
            CarDTO car = cars.get(i);
            contents += "    <option ";
            contents += "value=\"" + car.getId() + "\">" + car.toString() + "</option>\n";
        }
        contents += " </select>";
        contents += " company id: <select name=\"companyid\"/>\n";
        List<CompanyDTO> comps = daoComp.getAll();
        for(int i = 0; i < comps.size(); i++){
            CompanyDTO comp = comps.get(i);
            contents += "    <option ";
            contents += "value=\"" + comp.getId() + "\">" + comp.toString() + "</option>\n";
        }
        contents += " </select>";

        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

  private void printUpdateServiceForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    try{
        PrintWriter writer = response.getWriter();
        String htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);

        int id = Integer.parseInt(request.getParameter("id"));
        String contents = getFormHeader("update", "service");

        contents += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n";

        ServiceDTO serv = (ServiceDTO) daoServ.getById(id);
        contents += "Service record with id \"" + id + "\"";
        String tmpValueDate = " value=\"" + serv.getDate() + "\"";
        String tmpValuePrice = " value=\"" + serv.getPrice() + "\"";

        contents += " date: <input type=\"text\" name=\"date\" size=\"10\" placeholder=\"YYYY-MM-DD\" " + tmpValueDate + "/>\n";
        contents += " price: <input type=\"number\" name=\"price\" size=\"5\" placeholder=\"n.nn\" step=\"any\" " + tmpValuePrice + "/>\n";
        contents += " car id : <select name=\"carid\">\n";
        List<CarDTO> cars = daoCar.getAll();
        for(int i = 0; i < cars.size(); i++){
            CarDTO car = cars.get(i);
            contents += "    <option ";
            if(serv.getCar() == car.getId()){
                contents += "selected=\"selected\" ";
            }
            contents += "value=\"" + car.getId() + "\">" + car.toString() + "</option>\n";
        }
        contents += " </select>";
        contents += " company id: <select name=\"companyid\"/>\n";
        List<CompanyDTO> comps = daoComp.getAll();
        for(int i = 0; i < comps.size(); i++){
            CompanyDTO comp = comps.get(i);
            contents += "    <option ";
            if(serv.getCompany() == comp.getId()){
                contents += "selected=\"selected\" ";
            }
            contents += "value=\"" + comp.getId() + "\">" + comp.toString() + "</option>\n";
        }
        contents += " </select>";
        contents += getFormFooter();
        writer.println(contents);
        contents = getCancelForm();
        writer.println(contents);
        String htmlFooter  = getFooter();
        writer.println(htmlFooter);
    }catch(Exception e){
        throw e;
    }
  }

}
