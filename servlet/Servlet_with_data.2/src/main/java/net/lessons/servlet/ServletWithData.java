package net.lessons.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
    String action = "";
    String objType = null;

    String idStr = null;
    int id = 0;
    Boolean doget = true;
    try{
        if (request.getParameterMap().containsKey("action")) {
          action = request.getParameter("action");
        }
        if (request.getParameterMap().containsKey("object")) {
          objType = request.getParameter("object");
        }
        switch(action){
            case "createrequest":
                doget = false;
                printCreateUpdateForm(request, response);
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
                        String priceStr = request.getParameter("price");
                        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        if(checkDateFormat(dateStr) && checkPriceFormat(priceStr)){
                            Date date = java.sql.Date.valueOf(dateStr);
                            Float price = Float.parseFloat(priceStr);
                            String carStr = request.getParameter("carid");
                            int car = Integer.parseInt(carStr);
                            String compStr = request.getParameter("companyid");
                            int comp = Integer.parseInt(compStr);
                            daoServ.add(new ServiceDTO(date, price, car, comp));
                        }else{
                            doget = false;
                            printText(response, "Error: illegal value(s).");
                        }
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
                        String priceStr = request.getParameter("price");
                        if(checkDateFormat(dateStr) && checkPriceFormat(priceStr)){
                            Date date = java.sql.Date.valueOf(dateStr);
                            Float price = Float.parseFloat(priceStr);
                            String carStr = request.getParameter("carid");
                            int car = Integer.parseInt(carStr);
                            String compStr = request.getParameter("companyid");
                            int comp = Integer.parseInt(compStr);
                            daoServ.update(new ServiceDTO(id, date, price, car, comp));
                        }else{
                            doget = false;
                        }
                        break;
                }
                break;
            case "updaterequest":
                doget = false;
                printCreateUpdateForm(request, response);
                break;
            default:
                break;
        }
    }catch(NullPointerException e){
        String text = null;
        if(daoCar == null || daoComp == null || daoServ == null){
            text = "One or more DAO objects does not exist. Probably some problems with DB connection.";
        }else{
            text = "Error occured: " + e;
        }
        doget = false;
        printText(response, text);
    }catch(Exception e){
        doget = false;
        printText(response, "Error occured: " + e);
    }finally{
        if(doget){
            printMainPage(request, response);
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

  private void printCreateUpdateForm(HttpServletRequest request, HttpServletResponse response)
  throws Exception {
    String objType = request.getParameter("object");
    String action = request.getParameter("action");

    String idStr = null;
    int id = 0;
    String htmlHeader = null;
    String htmlFooter = null;
    PrintWriter writer = null;
    try{
        writer = response.getWriter();
        htmlHeader = getHeader("Servlet With Data");
        writer.println(htmlHeader);
        String nextAction = null;
        writer.println("Hello!");
        switch(action){
          case "createrequest":
            nextAction = "create";
            writer.println("You send create request for object type " + objType + ". ");
            break;
          case "updaterequest":
            idStr = request.getParameter("id");
            id = Integer.parseInt(idStr);
            nextAction = "update";
            writer.println("You send update request for object type " + objType + ". ");
            break;
          default:
            writer.println("Wrong action! Create or update request neaded. " + action + " is given.");
            throw new IOException("Wrong action! Create or update request neaded. " + action + " is given.");
        }
        writer.println("<br>");
        String contents = "<form action=\"./\" method=\"post\">\n" +
            "<input type=\"hidden\" name=\"action\" value=\"" + nextAction + "\"/>\n";
        if(nextAction == "update"){
            contents += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>\n";
        }
        contents += "<input type=\"hidden\" name=\"object\" value=\"" + objType + "\"/>\n";
        switch(objType){
            case "car":
                String tmpValueMark = "";
                String tmpValueModel = "";
                if(nextAction == "create"){
                  contents += "New Car ";
                }else{
                  CarDTO car = (CarDTO) daoCar.getById(id);
                  contents += "Car with id \"" + id + "\" ";
                  tmpValueMark = " value=\"" + car.getMark() + "\"";
                  tmpValueModel = " value=\"" + car.getModel() + "\"";
                }
                contents += " mark: <input type=\"text\" name=\"mark\" size=\"30\" placeholder=\"mark\" " + tmpValueMark + "/>\n" +
                    " model: <input type=\"text\" name=\"model\" size=\"30\" placeholder=\"model\" " + tmpValueModel + "/>\n";
                break;
            case "company":
                String tmpValueName = "";
                if(nextAction == "create"){
                  contents += "New Company ";
                }else{
                  CompanyDTO comp = (CompanyDTO) daoComp.getById(id);
                  contents += "Company with id \"" + id +"\"";
                  tmpValueName = "value=\"" + comp.getName() + "\"";
                }
                contents += " name: <input type=\"text\" name=\"name\" size=\"50\" placeholder=\"Company name\" " + tmpValueName + "/>\n";
                break;
            case "service":
                ServiceDTO serv = null;
                String tmpValueDate = "";
                String tmpValuePrice = "";
                if(nextAction == "create"){
                  contents += "New service record ";
                }else{
                  serv = (ServiceDTO) daoServ.getById(id);
                  contents += "Service record with id \"" + id + "\"";
                  tmpValueDate = " value=\"" + serv.getDate() + "\"";
                  tmpValuePrice = " value=\"" + serv.getPrice() + "\"";
                }
                contents += " date: <input type=\"text\" name=\"date\" size=\"10\" placeholder=\"YYYY-MM-DD\" " + tmpValueDate + "/>\n";
                contents += " price: <input type=\"number\" name=\"price\" size=\"5\" placeholder=\"n.nn\" step=\"any\" " + tmpValuePrice + "/>\n";
                contents += " car id : <select name=\"carid\">\n";
                List<CarDTO> cars = daoCar.getAll();
                for(int i = 0; i < cars.size(); i++){
                    CarDTO car = cars.get(i);
                    contents += "    <option ";
                    if(serv != null && serv.getCar() == car.getId()){
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
                    if(serv != null && serv.getCompany() == comp.getId()){
                        contents += "selected=\"selected\" ";
                    }
                    contents += "value=\"" + comp.getId() + "\">" + comp.toString() + "</option>\n";
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
    }catch(Exception e){
        throw e;
    }finally{
        if(writer != null){
            writer.flush();
            writer.close();
        }
    }
  }

  private boolean checkDateFormat(String date){
      Pattern p = Pattern.compile("^((19)|(20))[\\d]{2}-((1[0-2])|(0?[1-9]))-(([12][0-9])|(3[01])|(0?[1-9]))$");
      Matcher m = p.matcher(date);
      return m.matches();
  }

  private boolean checkPriceFormat(String price)
  throws IOException{
      try{
        Pattern p = Pattern.compile("^\\d+.?\\d*$");
        Matcher m = p.matcher(price);
        Float priceVal = Float.parseFloat(price);
        return (priceVal >= 0.00f) && m.matches();
      }catch(Exception e){
        throw new IOException("Price has illegal values");
      }
  }

  private void printText(HttpServletResponse response, String text)
  throws IOException{
        PrintWriter writer = response.getWriter();
        writer.println(text);
        writer.flush();
        writer.close();
  }

  private void printMainPage(HttpServletRequest request, HttpServletResponse response){
    PrintWriter writer = null;

    try{
      writer = response.getWriter();
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
        writer.flush();
        writer.println(text);
    } catch (Exception e) {
      writer.println("An error occured while retrieving data: " + e.toString());
    }finally {
      if(writer != null){
        writer.flush();
        writer.close();
      }
    }
  }
}
