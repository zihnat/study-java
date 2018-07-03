package net.lessons.dao;

public class CompanyDTO extends Object{
  private int id = -1;
  private String name;

  public CompanyDTO(String newName){
    setName(newName);
  }

  public CompanyDTO(int newId, String newName){
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
