package net.lessons.cassandra;


public class Car {

    private int id;
    private String mark;
    private String model;

    public Car() {
    }

    public Car(int id) {
	      this.setId(id);
    }
    public Car(int id, String mark, String model) {
	      this.setId(id);
        this.setMark(mark);
        this.setModel(model);
    }
    public Car(String mark, String model) {
	      this.setMark(mark);
        this.setModel(model);;
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

    public String toString(){
        return "{id: " + id + ", mark: \"" + mark + "\" , model: \"" + model + "\"}";
    }
}
