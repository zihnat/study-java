package net.lessons.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "services", schema = "example")
public class Company /*implements Serializable*/{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private int id;

  @Column(name = "name")
  private String name;

  public Company(){}

  public Company(String newName){
    setName(newName);
  }

  public Company(int newId, String newName){
    setId(newId);
    setName(newName);
  }

  public int getId(){
    return id;
  }

  public void setId(int newId){
    id = newId;
  }

  public String getName(){
    return name;
  }

  public void setName(String newName){
    name = newName;
  }

  public String toString(){
    if(id > -1){
      return "{id: " + id + ", company name: \"" + name + "\"}";
    }else{
      return "{id: undefined, company name: \"" + name + "\"}";
    }
  }
}
