package net.lessons.spring;

import net.lessons.spring.Lector;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component("cafedraPrototype")
@Scope("prototype")
public class Cafedra{

  private String cafedraName;
  private HashSet<Lector> lectors;

  public Cafedra(){
    lectors = new HashSet<Lector>();
  }

  public Cafedra(String name){
    cafedraName = name;
    lectors = new HashSet<Lector>();
  }

  public void setCafedraName(String name){
    cafedraName = name;
  }

  public String getCafedraName(){
    return cafedraName;
  }

  public void addLector(Lector lector){
    lectors.add(lector);
  }

  public void setLectors(HashSet<Lector> newLectors){
    lectors = newLectors;
  }

  public Set<Lector> getLectors(){
    return lectors;
  }

  @Override
  public String toString(){
    String result = "Cafedra name: " + cafedraName + "\n";
    if(lectors.size() > 0){
      result += "Lectors:\n";
      for(Lector lector : lectors){
        result += lector.toString() + "\n";
      }
    }
    return result;
  }
}
