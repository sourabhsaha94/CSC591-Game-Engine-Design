/*Change 1  is putting the first sleep method inside the synchronized block*/
package section2;

public class Section2Change1 implements Runnable {

	int i;
	boolean busy;
	Section2Change1 other;

	public Section2Change1(int i, Section2Change1 other) {
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
				synchronized (this) {
					Thread.sleep(2000);
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
		} else {
			while (other.isBusy()) {
				System.out.println("Waiting!");

				try {
					synchronized (other) {
						other.wait();
					}
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
			}
			System.out.println("Finished!");
		}
	}

	public static void main(String[] args) {
		Section2Change1 t1 = new Section2Change1(0, null);
		Section2Change1 t2 = new Section2Change1(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}