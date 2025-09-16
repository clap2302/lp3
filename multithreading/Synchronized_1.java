
public class Synchronized_1
{
    private static int count = 0;

    public static void main(String[] args)
    {
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
    }

    public static void printing()
    {
        synchronized (Synchronized_1.class)
        {
            count++;
            String name = Thread.currentThread().getName();
            System.out.println(name + " -> " + count);
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
