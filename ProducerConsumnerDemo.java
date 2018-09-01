package yu2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumnerDemo {
	public static void main(String[] args) {
		List<Integer> cake = new ArrayList<Integer>();
		new Thread(new Producer(cake)).start();
		new Thread(new Consumer(cake)).start();
	}
}
class Producer implements Runnable{
	static final int MAX_CAKE_NUM = 10;
	List<Integer> cake;
	Producer(){
		this.cake = null;
	}
	Producer(List<Integer> cake){
		this.cake = cake;
	}
	@Override
	public void run() {
		Random r = new Random(47);
		int temp;
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			synchronized (cake) {
				if(cake.size() < MAX_CAKE_NUM) {
					System.out.println("生产蛋糕："+(temp=r.nextInt(100)));
					cake.add(temp);
					cake.notify();
				}else {
					System.out.println("蛋糕库已满");
					try {
						cake.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
class Consumer implements Runnable{
	List<Integer> cake;
	Consumer(){
		this.cake = null;
	}
	Consumer(List<Integer> cake){
		this.cake = cake;
	}
	@Override
	public void run() {
		synchronized (cake) {
			while(true) {
				if(!cake.isEmpty()) {
					System.out.println("消费蛋糕："+cake.get(0));
					cake.remove(0);
					cake.notify();
				}else {
					System.out.println("蛋糕库已空");
					try {
						cake.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}