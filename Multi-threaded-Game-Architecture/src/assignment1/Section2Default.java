package assignment1;


//Demonstrating multi-threading and thread synchronization in Java
public class Section2Default implements Runnable {

	int i; // the ID of the thread, so we can control behavior
	boolean busy; // the flag, Thread 1 will wait until Thread 0 is no longer busy before continuing
	Section2Default other; // reference to the other thread we will synchronize on. This is needed so we can control behavior.

	// create the runnable object
	public Section2Default(int i, Section2Default other) {
		this.i = i; // set the thread ID (0 or 1)
		if(i==0) { busy = true; } // set the busy flag so Thread 1 waits for Thread 0
		else { this.other = other; }
	}

	// synchronized method to test if thread is busy or not
	public synchronized boolean isBusy() { return busy; } // What happens if this isn't synchronized? 

	// run method needed by runnable interface
	public void run() {
		if(i==0) { // 1st thread, sleep for a while, then notify threads waiting
			try {
				Thread.sleep(4000); // What happens if you put this sleep inside the synchronized block?
				synchronized(this) {
					notify(); // notify() will only notify threads waiting on *this* object;
				}
				Thread.sleep(4000); // What happens if you put this sleep inside the synchronized block?
				synchronized(this) {
					busy = false; // must synchronize while editing the flag
					notify(); // notify() will only notify threads waiting on *this* object;
				}
			}
			catch(InterruptedException tie) { tie.printStackTrace(); }
		}
		else {
			while(other.isBusy()) { // check if other thread is still working
				System.out.println("Waiting!");
				// must sychnronize to wait on other object
				try { synchronized(other) { other.wait(); } } // note we have synchronized on the object we are going to wait on
				catch(InterruptedException tie) { tie.printStackTrace(); }
			}
			System.out.println("Finished!");
		}
	}

	public static void main(String[] args) {
		Section2Default t1 = new Section2Default(0, null);
		Section2Default t2 = new Section2Default(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}