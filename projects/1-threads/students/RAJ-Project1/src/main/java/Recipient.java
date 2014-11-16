import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Recipient implements Runnable {

	String name;
	List <String> items = new ArrayList<String>();
	Random time = new Random();
	
	public Recipient(String name){
		this.name = name;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				TimeUnit.SECONDS.sleep(5);
				Chairman.registration(this);
			}
			catch(InterruptedException e){
				Chairman.printer.lock();
				System.out.print(name + " says good bye leaving with:");
				if (items.isEmpty()) System.out.print(" none items");
				else {
					for (String itemName: items.toArray(new String[items.size()]) )
					System.out.print( " " + itemName + ",");
				}
				System.out.print("\n");
				Chairman.printer.unlock();
				return;
			}
		}

	}
	
	public void win(String item) throws InterruptedException {
		System.out.println(name + " won " + item);
		items.add(item);
		int sec = time.nextInt(10)+5; //czeka od 5 do 15s
		TimeUnit.SECONDS.sleep(sec);
	}

}
