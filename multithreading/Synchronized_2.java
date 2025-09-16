public class Synchronized_2
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

    public static class MyRunnable implements Runnable
    {
        @Override
        public void run()
        {
            double squared_root;
            
            // Sync with Synchronized clas because the variable "count" its hers
            synchronized(Synchronized_2.class)
            {
                count++;
                squared_root = Math.sqrt(count);
            }

            System.out.println("Thread " + Thread.currentThread().getName() + " - result of " + count + " - " + squared_root);
        }
    }
}
