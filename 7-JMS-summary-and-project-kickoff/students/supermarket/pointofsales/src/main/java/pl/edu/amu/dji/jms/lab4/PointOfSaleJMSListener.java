package pl.edu.amu.dji.jms.lab4;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.core.JmsTemplate;

import com.rits.cloning.Cloner;
import com.google.common.base.Preconditions;

public class PointOfSaleJMSListener implements MessageListener {
	
    //public Map<String, Double> products = new HashMap<String, Double>();
    private final Cloner cloner = new Cloner();
    
    public Map<String, Double> products;
    
    public void setproducts(Map<String, Double> products){
    	this.products = products;
    }

    public void initProducts(Object object) {
    	Preconditions.checkState(object instanceof Map<?, ?>);
        this.products = cloner.deepClone((Map<String, Double>) object);
    }

    public void updatePrice(String name, Double price) {
        Preconditions.checkState(!products.isEmpty());

        products.put(name.toUpperCase(), price);
    }
	
	public void onMessage(Message message) {
        try{
            Preconditions.checkArgument(message instanceof MapMessage);

            MapMessage mapMessage = (MapMessage) message;
            String type = mapMessage.getString("type");
            if (type.equals("price change")){
            	updatePrice(mapMessage.getString("name"), mapMessage.getDouble("price"));
            }else{
            	initProducts(mapMessage.getObject("list"));
            	printListOfProducts();
            	System.out.println("Watek" + Thread.currentThread().getId());
            }
            
            
        } catch (JMSException ex){
            throw new IllegalStateException(ex);
        }

    }
	public void printListOfProducts(){
    	if (products.isEmpty()){
    		System.out.println("None of available products");
    	}else{
    		System.out.println("Available products: ");
            Set <String> keySet = products.keySet();
            for (String key : keySet){
            	System.out.println(key + " " + products.get(key));
            }
    	}
		
        
    }

	

	
}
