public class MyRunnable implements Runnable
{
    static int count = 0;

    // @Override
    // public void run()
    // {
    //     String name = Thread.currentThread().getName();
    //     System.out.println(name + " -> " + count++);
    // }

    /* The whole method is synchronized to be call in order */
    @Override
    public synchronized void run()
    {
        String name = Thread.currentThread().getName();
        System.out.println(name + " -> " + count++);
    }

    /* Only part of the code is synchronized to b executed in order */
    // @Override
    // public void run()
    // {
    //     synchronized(this)
    //     {
    //         String name = Thread.currentThread().getName();
    //         System.out.println(name + " -> " + count++);
    //     }
    // }
}
