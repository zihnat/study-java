package net.lessons.spring;

public class Lector{
  private String firstName;
  private String lastName;

  public Lector(String fName, String lName){
    setFirstName(fName);
    setLastName(lName);
  }

  public void setFirstName(String fname){
    firstName = fname;
  }

  public String getFirstName(){
    return firstName;
  }

  public void setLastName(String lname){
    lastName = lname;
  }

  public String getLastName(){
    return lastName;
  }

  @Override
  public String toString(){
    return firstName + " " + lastName;
  }
}
