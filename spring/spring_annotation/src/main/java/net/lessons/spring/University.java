package net.lessons.spring;

import net.lessons.spring.Cafedra;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("universityPrototype")
public class University{

  private String universityName;
  private HashSet<Cafedra> cafedras;

  @Autowired
  public University(String name){
    universityName = name;
    cafedras = new HashSet<Cafedra>();
  }

  public void setUniversityName(String name){
    universityName = name;
  }

  public String getUniversityName(){
    return universityName;
  }

  @Autowired
  public void setCafedras(HashSet<Cafedra> newCafedras){
    cafedras = newCafedras;
  }

  public void addCafedra(Cafedra cafedra){
    cafedras.add(cafedra);
  }

  public HashSet<Cafedra> getCafedras(){
    return cafedras;
  }

  @Override
  public String toString(){
    String result = "University name: " + universityName + "\n";
    if(cafedras.size() > 0){
      result += "Cafedras:\n";
      for(Cafedra cafedra : cafedras){
        result += cafedra.toString() + "\n";
      }
    }
    return result;
  }
}
