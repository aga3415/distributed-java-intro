package pl.edu.amu.dji.jms.lab4;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PointOfSalesApp {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        PointOfSalesJMSSender sender = (PointOfSalesJMSSender) context.getBean("pointOfSalesSender");
        
        System.out.println("Select products to sale: ");
        
        Scanner reader = new Scanner(System.in);
        String command;
        do{
        	command = reader.nextLine();
        	sender.sale(command.toUpperCase());
        	
        }while(!command.equals("CLOSE"));
        
        reader.close();
       
    }
	
}
