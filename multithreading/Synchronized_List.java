
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Synchronized_List
{

    private static List<String> list = new ArrayList<>();
    // private static List<String> list = new CopyOnWriteArrayList<>(); // copy consumes more memory

    public static void main(String[] args) throws InterruptedException
    {
        list = Collections.synchronizedList(list); // guarantee no ConcurrentModificationException

        MyRunnable runnable = new MyRunnable();
        System.out.println("\nRunnable threads started");

        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        
        System.out.println(list);
    }

    public static class MyRunnable implements Runnable
    {
        @Override
        public void run()
        {
            list.add("LP3");
            System.out.println("Thread " + Thread.currentThread().getName() + " - insert on list");
        }
    }
}
