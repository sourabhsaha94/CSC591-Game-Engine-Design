/*Change 3  is putting other.wait and notify outside the synchronized block*/
package section2;

public class Section2Change3 implements Runnable {

	int i;
	boolean busy;
	Section2Change3 other;

	public Section2Change3(int i, Section2Change3 other) {
		this.i = i;
		if (i == 0) {
			busy = true;
		} else {
			this.other = other;
		}
	}

	public synchronized boolean isBusy() {
		System.out.println(busy);
		return busy;
	}

	public void run() {
		if (i == 0) {
			try {
				Thread.sleep(2000);
				synchronized (this) {

				}
				notify();	//this is the change
				Thread.sleep(2000);
				synchronized (this) {
					busy = false;
				}
				notify();	//this is the change

			} catch (InterruptedException tie) {
				tie.printStackTrace();
			}
		} else {
			while (other.isBusy()) {
				System.out.println("Waiting!");
				try {
					other.wait();	//this is the change
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
			}
			System.out.println("Finished!");
		}
	}

	public static void main(String[] args) {
		Section2Change3 t1 = new Section2Change3(0, null);
		Section2Change3 t2 = new Section2Change3(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}