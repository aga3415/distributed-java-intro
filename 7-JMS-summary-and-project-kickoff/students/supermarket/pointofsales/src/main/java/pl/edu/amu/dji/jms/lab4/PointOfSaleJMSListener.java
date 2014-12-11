package pl.edu.amu.dji.jms.lab4;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.google.common.base.Preconditions;

public class PointOfSaleJMSListener implements MessageListener {
	
    private Map<String, Double> products = new ConcurrentHashMap<String, Double>();
     
    public Map<String, Double> getProducts(){
    	//System.out.println("Wątek przy getProducts: "+ Thread.currentThread().getName());
    	return products;
    }

    public void initProducts(Object object) {
    	try{
    		Preconditions.checkState(object instanceof Map<?, ?>);
			this.products = (Map<String, Double>) object; 
    	}catch(Exception ex){
    		System.out.println("Invalid list of products");
    	}
        
    }

    public void updatePrice(String name, Double price) {
        try{
        	Preconditions.checkState(!products.isEmpty());
        	products.put(name.toUpperCase(), price);
        	System.out.println("Update: " + name+ " " + price);
        }catch(Exception ex){
        	System.out.println("Empty list of products");
        }   
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
            	//System.out.println("Watek przy odbieraniu" + Thread.currentThread().getName());
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
