package net.lessons.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
@ComponentScan(basePackages="net.lessons.spring")
public class AppConfig {
   @Bean
   public String getName() {
      return "Programming Akademy";
   }

   @Bean
   public HashSet<Cafedra> getCafedras(){
     HashSet<Cafedra> cafedras = new HashSet<Cafedra>();
     Cafedra cafedra = new Cafedra("Mathematics");
     cafedra.addLector(new Lector("Ivan", "Ivanov"));
     cafedra.addLector(new Lector("Petr", "Petrov"));
     cafedras.add(cafedra);
     cafedra = new Cafedra("Phisics");
     cafedra.addLector(new Lector("Stepan", "Stepanov"));
     cafedras.add(cafedra);
     cafedra = new Cafedra("Programming");
     cafedra.addLector(new Lector("Semen", "Semenov"));
     cafedra.addLector(new Lector("Viktor", "Viktorov"));
     cafedra.addLector(new Lector("Igor", "Igorev"));
     cafedras.add(cafedra);
     cafedra = new Cafedra("English");
     cafedra.addLector(new Lector("John", "Smith"));
     cafedras.add(cafedra);
     return cafedras;
   }
}
