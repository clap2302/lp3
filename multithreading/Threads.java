
import java.util.ArrayList;

public class Threads
{
    public static void main(String[] args) throws InterruptedException
    {
        MyRunnable runnable = new MyRunnable();

        // Runnable lambdaRunnable = () ->
        // {
        //     for (int i = 1; i <= 5; i++)
        //     {
        //         System.out.println(Thread.currentThread().getName() + " - step " + i);
        //         try
        //         {
        //             Thread.sleep(500);
        //         } catch (InterruptedException e)
        //         {
        //             e.printStackTrace();
        //         }
        //     }
        // };

        // System.out.println("Lambda threads started");
        // Thread t1 = new Thread(lambdaRunnable);
        // Thread t2 = new Thread(lambdaRunnable);
        // Thread t3 = new Thread(lambdaRunnable);
        // t1.start();
        // t2.start();
        // t3.start();
        

        System.out.println("\nRunnable threads started");
        ArrayList <Thread> threads = new ArrayList<>();
    
        for (int i = 0; i < 5; i++)
        {
            threads.add(new Thread(runnable));
        }

        for (Thread thread : threads)
        {
            thread.start();
        }
    }
}
