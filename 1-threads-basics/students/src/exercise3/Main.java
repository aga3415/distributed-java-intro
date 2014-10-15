package exercise3;

public class Main {

    public static void main(String[] args) {

    	Thread [] array = new Thread[4];
    	
    	for (int i = 0; i < 4; i++ ){
    		array[i] = new Thread(new MyRunnable(), "Thread-" + Integer.toString(i));
    	}
    	
    	for (Thread w : array){
    		//w.run();
    		w.start();
    		try{
    			w.join();
    		}
    		catch (InterruptedException e){
    			
    		}
    		
    	}
    	System.out.println("FINISHED");
    	
    	/*try{
    		Thread.sleep(5000);
    	}
    	catch(InterruptedException e){
    		
    	}
    	System.out.println("FINISHED AGAIN");
    	*/
    	/*while (true){
    		try{
    			Thread.sleep(1000);
    		}
    		catch(InterruptedException e){
    			
    		}
    		if ( !(array[0].isAlive() || array[1].isAlive() || array[2].isAlive() || array[3].isAlive() )) {
    			System.out.println("non thread is alive");
    			break;
    		}
    	}*/
    	
    	/*while (true){
    		if ( !(array[0].isAlive() || array[1].isAlive() || array[2].isAlive() || array[3].isAlive() )) {
    			System.out.println("non thread is alive");
    			break;
    		}
    		try{
    			Thread.sleep(1000);
    		}
    		catch(InterruptedException e){
    			
    		}
    		
    	}*/
    	
    	
    	
    }
}
