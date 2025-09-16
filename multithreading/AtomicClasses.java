
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicClasses
{
    private static AtomicInteger count = new AtomicInteger(-1);
    private static AtomicBoolean bool = new AtomicBoolean(false);
    private static AtomicReference object = new AtomicReference<>();

    public static void main(String[] args)
    {
        MyRunnable runnable = new MyRunnable();
        System.out.println("\nRunnable threads started");

        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
    }

    public static void printing()
    {
        synchronized (Synchronized_1.class)
        {
            String name = Thread.currentThread().getName();
            System.out.println(name + " -> " + count.incrementAndGet());
        }
    }


    public static class MyRunnable implements Runnable
    {
        @Override
        public void run()
        {
            printing();
        }
    }
}
