import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderSystem {

    // Shared Queue
    static BlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();

    // Order Generator (Thread 1)
    static class OrderPlacer extends Thread {
        String[] items = {"Book", "Pen", "Laptop", "Phone"};

        public void run() {
            try {
                for (int i = 0; i < items.length; i++) {
                    String order = items[i];
                    System.out.println("Order placed: " + order);
                    orderQueue.put(order);  // Add to queue
                    Thread.sleep(1000);     // Wait 1 sec
                }
            } catch (InterruptedException e) {
                System.out.println("OrderPlacer stopped.");
            }
        }
    }

    // Order Processor (Thread 2)
    static class OrderDeliverer extends Thread {
        public void run() {
            try {
                while (true) {
                    String order = orderQueue.take(); // Take from queue
                    System.out.println("Delivering: " + order);
                    Thread.sleep(1500);               // Simulate delivery
                    System.out.println("Delivered: " + order);
                }
            } catch (InterruptedException e) {
                System.out.println("OrderDeliverer stopped.");
            }
        }
    }

    // Main Method
    public static void main(String[] args) {
        OrderPlacer placer = new OrderPlacer();
        OrderDeliverer deliverer = new OrderDeliverer();

        placer.start();
        deliverer.start();
    }
}
