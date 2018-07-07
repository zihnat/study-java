package net.lessons.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cars", schema = "example")
public class CarDTO{

  private int id;
  private String mark;
  private String model;
  private Set<ServiceDTO> services = new HashSet<ServiceDTO>();

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

  public CarDTO(int newId, String newMark, String newModel, Set<ServiceDTO> newServices) {
    setId(newId);
    setMark(newMark);
    setModel(newModel);
    setServices(newServices);
  }

  public CarDTO(String newMark, String newModel, Set<ServiceDTO> newServices){
    setMark(newMark);
    setModel(newModel);
    setServices(newServices);
  }

  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  public int getId(){
    return id;
  }
  public void setId(int newId){
    id = newId;
  }


  @Column(name = "mark")
  public String getMark(){
    return mark;
  }
  public void setMark(String newVal){
    mark = newVal;
  }


  @Column(name = "model")
  public String getModel(){
    return model;
  }
  public void setModel(String newVal){
    model = newVal;
  }


  @OneToMany(fetch = FetchType.EAGER, mappedBy="car", cascade = CascadeType.ALL)
  public Set<ServiceDTO> getServices(){
    return services;
  }
  public void setServices(Set<ServiceDTO> newServices){
    services = newServices;
  }


  public String toString(){
    if(id > -1){
      String res = "{id: " + id + ", mark: \"" + mark + "\" , model: \"" + model + "\", services: [";
      for (ServiceDTO serv : services) {
        res += "\n  " + serv;
      }
      res += "]}";
      return res;
    }else{
      String res = "{id: undefined, mark: \"" + mark + "\" , model: \"" + model + "\", services: [";
      for (ServiceDTO serv : services) {
        res += "\n  " + serv;
      }
      res += "]}";
      return res;
    }
  }
}
