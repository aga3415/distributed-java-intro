package pl.edu.amu.dji.jms.lab4;

import java.util.HashMap;
import java.util.Map;

import javax.jms.*;

import org.springframework.jms.IllegalStateException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.google.common.base.Preconditions;

public class WarehouseJMS /*implements MessageListener*/{
	
	private JmsTemplate jmsTemplate;
	public static final Map<String, Double> PRODUCTS;

    static {
        PRODUCTS = new HashMap<String, Double>();
        PRODUCTS.put("A", 10.99);
        PRODUCTS.put("B", 9.99);
    }


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    private Destination POS;
    
    public void setPOS(Destination POS) {
        this.POS = POS;
    }

    public void changePrice(String name, Double price) {
        name = name.toUpperCase();
        Double oldPrice;
        try{
        	oldPrice = PRODUCTS.get(name);
        	
        }catch(NullPointerException ex){
        	System.out.println("New product will be created");
        }
        PRODUCTS.put(name, price);
        
        informPOSaboutPriceChange(name, price);
        //jeśli cena jest taka sama i tak informuje sklepy
     
    }
    
    public void informPOSaboutPriceChange(final String name, final Double price){
    	
    	jmsTemplate.send(POS, new MessageCreator() {
		    
    		public Message createMessage(Session session) throws JMSException {
		        
    			MapMessage priceIsChanging = session.createMapMessage();
		        priceIsChanging.setString("type", "price change");
		        priceIsChanging.setString("name", name);
		        priceIsChanging.setDouble("price", price);

		        return priceIsChanging;
		    }
    	});
    }
    
    public void sendFullProductList(){
    	
    	jmsTemplate.send(POS, new MessageCreator() {
    		
		    public Message createMessage(Session session) throws JMSException {
		    	
		        MapMessage listIsSending = session.createMapMessage();
		        listIsSending.setString("type", "full price list");
		        listIsSending.setObject("list", PRODUCTS);
		        
		        return listIsSending;
		    }
		});
    }



}
