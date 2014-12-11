package pl.edu.amu.dji.jms.lab4;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.*;

public class WarehouseApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        WarehouseJMS wh = (WarehouseJMS) context.getBean("warehouse");
        
        String command = "";
        Scanner read = new Scanner(System.in);
        
        while (!command.equals("CLOSE")){
        	System.out.println("What do you want to do?"
        			+ "\n- CLOSE the warehouse"
        			+ "\n- SEND FULL PRODUCTS LIST"
        			+ "\n- CHANGE PRICE" );
        	
			command = read.nextLine().toUpperCase();
        	
			if (command.equals("CHANGE PRICE")){
        		System.out.println("Give the product's name and price: ");
        		
        		try{
        			wh.changePrice(read.next(), read.nextDouble());
        		}catch(InputMismatchException ex){
        			System.out.println("Data is incorrect");
        		}
        		      		
        	}else{
        		if(command.equals("SEND FULL PRODUCTS LIST")){
        			wh.sendFullProductList();
        		}
        		else{
        			System.out.println("Invalid command");
        		}
        	}

        }
        read.close();
    }
}
