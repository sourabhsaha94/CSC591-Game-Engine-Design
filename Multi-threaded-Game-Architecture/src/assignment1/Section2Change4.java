/*Change 4  is creating a new thread which waits for the previous thread (which in turn is waiting for its previous thread)*/
package assignment1;

public class Section2Change4 implements Runnable {

	int i;
	boolean busy;
	Section2Change4 other;

	public Section2Change4(int i, Section2Change4 other) {
		this.i = i;
		if (i == 0) {
			busy = true;
		} else {
			busy = true;
			this.other = other;
		}
	}

	public synchronized boolean isBusy() {
		return busy;
	}

	public void run() {
		if (i == 0) {
			try {
				Thread.sleep(2000);
				synchronized (this) {
					notify();
				}
				Thread.sleep(2000);
				synchronized (this) {
					busy = false;
					notify();
				}

			} catch (InterruptedException tie) {
				tie.printStackTrace();
			}
		} else if (i == 1) {
			while (other.isBusy()) {
				System.out.println(i + " is Waiting!");
				try {
					synchronized (other) {
						other.wait();
					}
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
			}

			synchronized (this) {
				busy = false;
				notify();
			}

			System.out.println(i + " has Finished!");
		} else {
			while (other.isBusy()) {
				System.out.println(i + " is Waiting!");
				try {
					synchronized (other) {
						other.wait();
					}
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
				System.out.println(i + " has Finished!");
			}
		}
	}

	public static void main(String[] args) {
		Section2Change4 t1 = new Section2Change4(0, null);
		Section2Change4 t2 = new Section2Change4(1, t1);
		Section2Change4 t3 = new Section2Change4(2, t2);
		(new Thread(t3)).start();
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}