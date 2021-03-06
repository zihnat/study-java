package net.lessons.dao;

//import java.text.SimpleDateFormat;
import java.sql.Date;

public class ServiceDTO extends Object{
  private int id = -1;
  private Date date;
  private Float price;
  private int carId;
  private int compId;

  public ServiceDTO(int newId, Date newDate, Float newPrice, int newCar, int newComp){
    setId(newId);
    setDate(newDate);
    setPrice(newPrice);
    setCar(newCar);
    setCompany(newComp);
  }
  
  public ServiceDTO(Date newDate, Float newPrice, int newCar, int newComp){
    setDate(newDate);
    setPrice(newPrice);
    setCar(newCar);
    setCompany(newComp);
  }

  public int getId(){
    return id;
  }

  private void setId(int newId){
    id = newId;
  }
  
  public Date getDate(){
      return date;
  }
  
  public void setDate(Date newDate){
      date = newDate;
  }
  
  public Float getPrice(){
      return price;
  }
  
  public void setPrice(Float newPrice){
      price = newPrice;
  }
  
  public int getCar(){
      return carId;
  }
  
  public void setCar(int car){
      carId = car;
  }
  
  public int getCompany(){
      return compId;
  }
  
  public void setCompany(int company){
      compId = company;
  }

  public String toString(){
    if(id > -1){
      return "{id: " + id + ", date: \"" + date + "\", price: \"" + price+ "\", car id: \"" + carId + "\", company id: \"" + compId + "\"}";
    }else{
      return "{id: undefined, date: \"" + date + "\", price: \"" + price+ "\", car id: \"" + carId + "\", company id: \"" + compId + "\"}";
    }
  }
}
