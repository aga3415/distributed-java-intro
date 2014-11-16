import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Donor implements Runnable {

	Random time = new Random();
	String name;
	List <String> items = new ArrayList<String>();
	
	public Donor (String name, List<String> items){
		this.name = name;
		this.items = items;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			try{
				int waiting = time.nextInt(25)+5;	//czeka od 0 do 30s na rejestracje
				TimeUnit.SECONDS.sleep(waiting);
				while (!Chairman.giveItem(this)){ //jesli kolejka zarejestrowanych przedmiotow jest pelna czeka i probuje ponownie
					TimeUnit.SECONDS.sleep(5);
					//System.out.println(name + "says: Kolejka pelna");
				}
			}
			catch(InterruptedException e){
				Chairman.printer.lock();
				System.out.println(this.name + " says good bye");
				Chairman.printer.unlock();
				return;
			}
		}

	}

}
