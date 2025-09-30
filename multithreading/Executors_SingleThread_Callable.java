
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Executors_SingleThread_Callable
{
    public static void main(String[] args) throws ExecutionException
    {
        ExecutorService executor = null;
        try
        {
            executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new MyCallable());

            System.out.println(future.isDone());
            System.out.println(future.get());
            executor.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println(future.isDone());
        }

        catch (InterruptedException e) {}

        finally
        {
            if (executor != null)
                executor.shutdownNow();
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
