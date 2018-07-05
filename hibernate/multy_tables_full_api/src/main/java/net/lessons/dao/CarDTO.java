package net.lessons.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cars", schema = "example")
public class CarDTO{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private int id;

  @Column(name = "mark")
  private String mark;
  @Column(name = "model")
  private String model;

  public CarDTO(){}
  
  public CarDTO(int newId, String newMark, String newModel) {
    setId(newId);
    setMark(newMark);
    setModel(newModel);
  }

  public CarDTO(String newMark, String newModel){
    setMark(newMark);
    setModel(newModel);
  }

  public void setId(int newId){
    id = newId;
  }

  public int getId(){
    return id;
  }

  public void setMark(String newVal){
    mark = newVal;
  }

  public String getMark(){
    return mark;
  }

  public void setModel(String newVal){
    model = newVal;
  }

  public String getModel(){
    return model;
  }

  public String toString(){
    if(id > -1){
      return "{id: " + id + ", mark: \"" + mark + "\" , model: \"" + model + "\"}";
    }else{
      return "{id: undefined, mark: \"" + mark + "\" , model: \"" + model + "\"}";
    }
  }
}
