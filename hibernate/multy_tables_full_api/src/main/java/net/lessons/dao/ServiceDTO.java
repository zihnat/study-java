package net.lessons.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "service", schema = "example")
public class ServiceDTO{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private int id;
  @Column(name = "service_date")
  private Date date;
  @Column(name = "price")
  private Float price;

  @Column(name = "car_id")
  private int car;

  @ManyToOne
  @JoinColumn(name = "service_id", nullable = false)
  private CompanyDTO comp;

  public ServiceDTO(){}

  public ServiceDTO(int newId, Date newDate, Float newPrice, int newCar, CompanyDTO newComp){
    setId(newId);
    setDate(newDate);
    setPrice(newPrice);
    setCar(newCar);
    setCompany(newComp);
  }

  public ServiceDTO(Date newDate, Float newPrice, int newCar, CompanyDTO newComp){
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
      return car;
  }

  public void setCar(int newCar){
      car = newCar;
  }

  public CompanyDTO getCompany(){
      return comp;
  }

  public void setCompany(CompanyDTO company){
      comp = company;
  }

  public String toString(){
    if(id > -1){
      return "{id: " + id + ", date: \"" + date + "\", price: \"" + price+ "\", car : \"" + car + "\", company : " + comp + "}";
    }else{
      return "{id: undefined, date: \"" + date + "\", price: \"" + price+ "\", car : \"" + car + "\", company : " + comp + "}";
    }
  }
}
