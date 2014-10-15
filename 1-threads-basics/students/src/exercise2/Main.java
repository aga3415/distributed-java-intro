package exercise2;

public class Main {

    public static void main(String[] args) {
    	
    	Thread [] array = new Thread [4];
    	for ( int i = 0; i < 4; i++ ){
    		array[i] = new MyThread("Thread-" + Integer.toString(i));
    	}
    	
    	for (Thread w: array ){
    		//w.run();
    		w.start();
    	}
    	
    	
    }
}
