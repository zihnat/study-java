package net.lessons.spring;

import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main{

	public static void main(String[] args) {
    //ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml"); // can be used as well
		ApplicationContext context = new AnnotationConfigApplicationContext("net.lessons.spring");
		Lector lector1 = (Lector) context.getBean("lectorPrototype");
		lector1.setFirstName("Ivan");
		lector1.setLastName("Ivanov");
		//System.out.println(lector1);
		Lector lector2 = (Lector) context.getBean("lectorPrototype");
		lector2.setFirstName("Petr");
		lector2.setLastName("Petrov");
		Cafedra cafedra1 = (Cafedra) context.getBean("cafedraPrototype");
		cafedra1.setCafedraName("Mathematics");
		cafedra1.addLector(lector1);
		cafedra1.addLector(lector2);
		Lector lector3 = (Lector) context.getBean("lectorPrototype");
		lector3.setFirstName("Stepan");
		lector3.setLastName("Stepanov");
		Cafedra cafedra2 = (Cafedra) context.getBean("cafedraPrototype");
		cafedra2.setCafedraName("Phisics");
		cafedra2.addLector(lector3);
		Lector lector4 = (Lector) context.getBean("lectorPrototype");
		lector4.setFirstName("Semen");
		lector4.setLastName("Semenov");
		Lector lector5 = (Lector) context.getBean("lectorPrototype");
		lector5.setFirstName("Viktor");
		lector5.setLastName("Voktorov");
		Lector lector6 = (Lector) context.getBean("lectorPrototype");
		lector6.setFirstName("Igor");
		lector6.setLastName("Igorev");
		Cafedra cafedra3 = (Cafedra) context.getBean("cafedraPrototype");
		cafedra3.setCafedraName("Programming");
		cafedra3.addLector(lector4);
		cafedra3.addLector(lector5);
		cafedra3.addLector(lector6);
		Lector lector7 = (Lector) context.getBean("lectorPrototype");
		lector7.setFirstName("John");
		lector7.setLastName("Smith");
		Cafedra cafedra4 = (Cafedra) context.getBean("cafedraPrototype");
		cafedra4.setCafedraName("English");
		cafedra4.addLector(lector7);
		University university = (University) context.getBean("universityPrototype");
		university.setUniversityName("Programming Akademy");
		university.addCafedra(cafedra1);
		university.addCafedra(cafedra2);
		university.addCafedra(cafedra3);
		university.addCafedra(cafedra4);

		System.out.println(university);
	}

}
