package pl.edu.amu.dji.jms.lab4.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.google.common.base.Preconditions;

public class ReportingJMS implements MessageListener{

	public static final Map<String, Double> PRODUCTS = new HashMap<String, Double>();

	public void updateReport(String name, Double price) {
		Double current = PRODUCTS.get(name);
		
		current = current != null ? current + price : price;

	    PRODUCTS.put(name, current);
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		 try{
	            Preconditions.checkArgument(message instanceof MapMessage);

	            MapMessage mapMessage = (MapMessage) message;
	            String name = mapMessage.getString("name");
	            Double price = mapMessage.getDouble("price");
	            updateReport(name, price);
	            
	            System.out.println("sales: " + name + " for " + price);
	            
	        } catch (JMSException ex){
	            throw new IllegalStateException(ex);
	        }
		
	}
	
	public void printReport(){
		System.out.println("Report: ");
		Set<String> keySet = PRODUCTS.keySet();
		for (String name: keySet){
			System.out.println(name + " " + PRODUCTS.get(name));
		}
	}
}
