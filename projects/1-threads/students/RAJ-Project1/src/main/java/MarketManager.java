import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MarketManager {

	private static ExecutorService threads = Executors.newCachedThreadPool();
	private List<String> userNames = new ArrayList<String>();
	private List<String> itemNames = new ArrayList<String>();
	Random random = new Random();
	private static int donorsCounter = 0;
	private static int recipientsCounter = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarketManager manager = new MarketManager();
		threads.execute(new Chairman(manager));
		for (int i = 0; i < 10; i++){
			manager.createDonor();
		}
		for (int i = 0; i < 5; i++){
			manager.createRecipient();
		}


	}
	
	public void closeMarket(){
		threads.shutdownNow();
	}
	
	private void donorRegister(String name, List<String> items){
		if(userNames.contains(name)){
			return;
		}
		for(String item : items.toArray(new String[items.size()])){
			if(itemNames.contains(item)){
				items.remove(item);
			}
		}
		if (!items.isEmpty()){
			threads.execute(new Donor(name, items));
		}
	}
	
	private void recipientRegister(String name){
		if(userNames.contains(name)){
			return;
		}
		threads.execute(new Recipient(name));
	}
	
	private void createDonor(){
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < random.nextInt(2); i++){
			items.add("D"+ Integer.toString(donorsCounter) + "item" + Integer.toString(i));
		}
		donorRegister("Donor"+Integer.toString(donorsCounter),items );
		donorsCounter++;
	}
	
	private void createRecipient(){
		recipientRegister("Recipient" + Integer.toString(recipientsCounter));
		recipientsCounter++;
	}

}
