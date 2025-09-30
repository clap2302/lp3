
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executors_SingleThread_Runnable
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new MyRunnable());
        try
        {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }

        catch (InterruptedException e) {}

        finally
        {
            executor.shutdown();

        }
    }

    
    public static class MyRunnable implements Runnable
    {
        @Override
        public void run()
        {
            String name = Thread.currentThread().getName();
            System.out.println(name + "LP-III");
        }
    }

    public static class MyCallable implements Callable<String>
    {
        @Override
        public String call()
        {
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(100);
            return name + ":LP-III" + nextInt;
            
        }
    }
}
