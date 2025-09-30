
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executors_MultiThread
{
    public static void main(String[] args)
    {
        ExecutorService executor = null;

        try
        {
            executor = Executors.newFixedThreadPool(4);
            // executor = Executors.newCachedThreadPool();

            Future<String> f1 = executor.submit(new Task());
            Future<String> f2 = executor.submit(new Task());
            Future<String> f3 = executor.submit(new Task());
            
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());

            executor.shutdown();

        }

        catch (InterruptedException | ExecutionException e){ System.out.println(e);}

        finally
        {
            if (executor != null)
                executor.shutdownNow();
        }
    }

    public static class Task implements Callable<String>
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
