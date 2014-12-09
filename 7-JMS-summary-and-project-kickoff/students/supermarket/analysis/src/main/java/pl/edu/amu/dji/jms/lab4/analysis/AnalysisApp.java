package pl.edu.amu.dji.jms.lab4.analysis;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnalysisApp {
    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        ReportingJMS rep = (ReportingJMS) context.getBean("reportingJMSClass");
        
        Scanner reader = new Scanner(System.in);
        System.out.println("Press enter to show report");
        String command = reader.nextLine();
        while (!command.toUpperCase().equals("EXIT")){
        	rep.printReport();
        	command = reader.nextLine();
        }
    }
}
