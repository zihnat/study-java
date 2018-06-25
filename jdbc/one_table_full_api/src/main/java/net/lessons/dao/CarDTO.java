package net.lessons.dao;

public class CarDTO {
  private int id = -1;
  private String mark;
  private String model;

  public CarDTO(int newId, String newMark, String newModel) {
    setId(newId);
    setMark(newMark);
    setModel(newModel);
  }

  public CarDTO(String newMark, String newModel){
    setMark(newMark);
    setModel(newModel);
  }

  private void setId(int newId){
    id = newId;
  }

  public void setMark(String newVal){
    mark = newVal;
  }

  public void setModel(String newVal){
    model = newVal;
  }

  public int getId(){
    return id;
  }

  public String getMark(){
    return mark;
  }

  public String getModel(){
    return model;
  }

  @Override
  public String toString(){
    if(id > -1){
      return "{id: " + id + ", mark: \"" + mark + "\" , model: \"" + model + "\"}";
    }else{
      return "{id: undefined, mark: \"" + mark + "\" , model: \"" + model + "\"}";
    }
  }
}
