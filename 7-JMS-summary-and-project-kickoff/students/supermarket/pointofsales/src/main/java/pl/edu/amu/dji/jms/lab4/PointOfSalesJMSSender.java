package pl.edu.amu.dji.jms.lab4;

import java.util.Set;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class PointOfSalesJMSSender {
	
	private PointOfSaleJMSListener listener;
	private JmsTemplate jmsTemplate;
	private Destination reporting;
		
	public PointOfSalesJMSSender(PointOfSaleJMSListener listener){
		this.listener = listener;
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    public void setReporting(Destination reporting) {
        this.reporting = reporting;
    }
    
    public void sale(final String name) {
    	
    	if (listener.getProducts().isEmpty()){
    		System.out.println("None of available products");
    	}else{
    		
    		System.out.println("Available products: ");
            Set <String> keySet = listener.getProducts().keySet();
            //System.out.println("Watek sendera : " +Thread.currentThread().getName());
            for (String key : keySet){
            	System.out.println(key + " " + listener.getProducts().get(key));
            }
            
            try{
            	final Double price = listener.getProducts().get(name.toUpperCase());
                
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
    
	

}
