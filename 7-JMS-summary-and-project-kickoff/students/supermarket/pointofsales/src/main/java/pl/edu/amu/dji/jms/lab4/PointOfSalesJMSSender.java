package pl.edu.amu.dji.jms.lab4;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.google.common.base.Preconditions;

public class PointOfSalesJMSSender {
	
	//public Map<String, Double> products = new HashMap<String, Double>();
	private PointOfSaleJMSListener listener;
	private JmsTemplate jmsTemplate;
	private Destination reporting;
	
	public Map<String, Double> products;
    
    public void setproducts(Map<String, Double> products){
    	this.products = products;
    }
	
	public PointOfSalesJMSSender(PointOfSaleJMSListener listener){
		this.products = listener.products;
		this.listener = listener;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    public void setReporting(Destination reporting) {
        this.reporting = reporting;
    }
    
    public void sale(final String name) {
    	if (products.isEmpty()){
    		System.out.println("None of available products");
    	}else{
    		System.out.println("Available products: ");
            Set <String> keySet = products.keySet();
            for (String key : keySet){
            	System.out.println(key + " " + products.get(key));
            }
    	}
        Preconditions.checkState(!products.isEmpty());
        try{
        	final Double price = products.get(name.toUpperCase());
            
        	
            jmsTemplate.send(reporting, new MessageCreator() {
            		
            	public Message createMessage(Session session) throws JMSException {
        		    	
            		MapMessage productIsSaled = session.createMapMessage();
        		    productIsSaled.setString("id", Long.toString(Thread.currentThread().getId()));
        		    productIsSaled.setString("name", name);
        		    productIsSaled.setDouble("price", price);
        		        
        		    return productIsSaled;
        		}
        	});
        }catch(NullPointerException ex){
        	System.out.println("Product " + name + " is unavailable");
        }
        
   
    }
    
	

}
