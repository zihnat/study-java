package net.lessons.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main{

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		University university = (University) context.getBean("universityPrototype");
		System.out.println(university);
	}

}
