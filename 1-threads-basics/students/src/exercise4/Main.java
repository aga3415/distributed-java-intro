package exercise4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
    	
    	ExecutorService pulaWatkow = Executors.newFixedThreadPool(4);
    	
    	for ( int i = 0; i < 4; i++ ){
    		pulaWatkow.submit(new MyRunnable());
    	}
    	pulaWatkow.shutdown();
    	System.out.println("FINISHED");
    	try {
			pulaWatkow.awaitTermination(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
