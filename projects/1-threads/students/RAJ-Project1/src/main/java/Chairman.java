import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Chairman implements Runnable{

	MarketManager manager;
	private static List<String> items = new ArrayList<String>();
	private static Lock donorsLock = new ReentrantLock();
	public static Lock printer = new ReentrantLock();
	public static Lock recipients = new ReentrantLock();
	public static Condition condition = recipients.newCondition();
	public static Condition conditionD = donorsLock.newCondition();
	private static List<Recipient> users = new ArrayList<Recipient>();
	private static Random findWinner = new Random();
	
	
	public Chairman(MarketManager manager){
		this.manager = manager;
	}

	public void run() {
		while(true){
			try{
				TimeUnit.SECONDS.sleep(5);
				//Przy takim okresie oczekiwania na aukcje darczyncy maja male szanse na oddanie przedmiotu
				// donors wait 0 to 30s before registering the item
				auction();
			}
			catch(InterruptedException e){
				printer.lock();
				System.out.println("Chairman says good bye");
				printer.unlock();
				return;
			}
		}
	}
	
	public static Boolean giveItem(Donor donor) throws InterruptedException{
		
		donorsLock.lock();
		if (items.size() >= 10) {
			donorsLock.unlock();
			return false;
		}
		if (donor.items.isEmpty()){
			conditionD.await();
		}
		else{
			items.add(donor.items.get(0));
			donor.items.remove(0);
		}
		
		donorsLock.unlock();
		
		return true;
	}
	
	public static Boolean registration(Recipient rec) throws InterruptedException{
		recipients.lock();
		if (users.size() >= 10){
			recipients.unlock();
			return false;
		}
		
		users.add(rec);
		
		printer.lock();
		System.out.println("Registering " + rec.name);
		printer.unlock();
		
		condition.await();
		recipients.unlock();
		
		return true;

	}
	
	public void auction() throws InterruptedException{
		if (items.isEmpty()){
			TimeUnit.SECONDS.sleep(5);
		}
		if (items.isEmpty()){
			System.out.println("No auctions within 5 seconds. Closing the market");
			donorsLock.lock();
			conditionD.signalAll();
			donorsLock.unlock();
			recipients.lock();
			condition.signalAll();
			recipients.unlock();
			manager.closeMarket();
			return;
		}
		
		recipients.lock();
		if (users.isEmpty()) {
			System.out.println("There is no winner for " + items.get(0));
			items.remove(0);
		}
		else{
			int nr = findWinner.nextInt(users.size());
			System.out.println("Winner for auction " + items.get(0) + " is " + users.get(nr).name);
			users.get(nr).win(items.get(0));
			users.clear();
			condition.signalAll();
			items.remove(0);
			users.clear();
			
		}
		recipients.unlock();
		
	}

}
