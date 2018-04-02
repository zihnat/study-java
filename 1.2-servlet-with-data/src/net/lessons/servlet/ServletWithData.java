package net.lessons.servlet;

import java.io.IOException;
import java.io.PrintWriter;

//import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;

public class ServletWithData extends HttpServlet {
  /**
   * this life-cycle method is invoked when this servlet is first accessed
   * by the client
   */
  public void init(ServletConfig config) {
    System.out.println("Servlet is being initialized");
  }

  /**
   * handles HTTP GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer    = response.getWriter();
    Connection connection = null ;
    Statement statement   = null;

    Vector<String> head = new Vector<String>();
    head.addElement("id");
    head.addElement("name");
    head.addElement("age");
    head.addElement("cost");

    try{
      connection   = getMySQLConnection();
      statement    = connection.createStatement();
      String query = "select * from list";
      ResultSet rs = statement.executeQuery(query);

      String htmlHeader  = getHeader("Servlet With Data");
      String tableHeader = getTableHeader(head);
      String tableRows   = getTableRow(head, rs);
      String tableFooter = getTableFooter();
      String htmlFooter  = getFooter();
      writer.println(htmlHeader);
      writer.println(tableHeader);
      writer.println(tableRows);
      writer.println(tableFooter);
      writer.println(htmlFooter);

      //writer.println("<html>Hello from Simple Java servlet!</html>");

    } catch (SQLException e) {
      writer.println("An error occured while retrieving data: "+ e.toString());
    } catch (ClassNotFoundException e){
      writer.println("Class not foud: "+ e.toString());
    }finally {
      writer.flush();
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {

      }
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


  public Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
    String hostName = "localhost";       //mysql server address
    String dbName   = "servlet";         //database name
    String userName = "user";            //database user
    String password = "passwordForUser"; //database user password
    return getMySQLConnection(hostName, dbName, userName, password);
  }

  public Connection getMySQLConnection(String hostName, String dbName,
  String userName, String password) throws SQLException,
  ClassNotFoundException {
    // This is necessary with Java 5 (or older)
    Class.forName("com.mysql.jdbc.Driver");

    String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
    Connection conn      = DriverManager.getConnection(connectionURL, userName, password);
    return conn;
  }

  private String getHeader(String title){
    String htmlHeader = null;
    htmlHeader = "<HTML><HEAD><TITLE> " + title + " </TITLE></HEAD><BODY>";
    return htmlHeader;
  }
  private String getFooter(){
    String htmlFooter = "</BODY></HTML>";
    return htmlFooter;
  }
  private String getTableHeader(Vector<String> head){
    String tableHeader = null;
    tableHeader = "<TABLE border=1>";
    String tableHead   = getTableHead(head);
    return tableHeader + tableHead;
  }
  private String getTableFooter(){
    String tableHeader = "</TABLE>";
    return tableHeader;
  }
  private String getTableHead(Vector<String> head){
    int vsize = head.size();
    String contents = new String();
    contents = "<tr>";
    for(int i = 0; i < vsize; i++){
      String value = head.elementAt(i).toString();
      contents += "<th>" + value +"</th>";
    }
    contents += "</tr>\n";
    return contents;
  }
  private String getTableRow(Vector<String> head, ResultSet rs) throws SQLException{
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
  }
}
