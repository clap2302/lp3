public class MyRunnable implements Runnable
{
    static int count = 0;
    @Override
    public void run()
    {
        String name = Thread.currentThread().getName();
        System.out.println(name + " -> " + count++);
    }
}