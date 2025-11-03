import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerInitialized
{
    CountDownLatch latch = new CountDownLatch(4);

    private void waitForInitialization()
    {
        try
        {
            latch.await();
        
        }
        catch (InterruptedException e){ System.out.println(e);}
    }

    private void startServer()
    {
        System.out.println("Servidor Principal Online: Pronto para aceitar conexões (Socket.bind())");
    }

    public static void main(String[] args)
    {
        ServerInitialized server = new ServerInitialized();
        
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(new ModuleLoader(server.latch, "Configuração", 6000));
        executor.submit(new ModuleLoader(server.latch, "Cache", 9000));
        executor.submit(new ModuleLoader(server.latch, "Chaves", 12000));
        executor.submit(new ModuleLoader(server.latch, "Log", 4000));
        
        server.waitForInitialization();
        server.startServer();

    }

    public static class ModuleLoader implements Runnable
    {
        private CountDownLatch latch;
        private String name;
        private int loadTime;

        public ModuleLoader(CountDownLatch latch, String name, int loadTime)
        {
            this.latch = latch;
            this.name = name;
            this.loadTime = loadTime;
        }

        @Override
        public void run()
        {
            try
            {
                // Simulate module loading
                System.out.println("[CARREGANDO] Módulo " + this.name + " iniciando...");
                Thread.sleep(this.loadTime);
                System.out.println("[OK] Módulo " + this.name + " carregado.");
            }
            catch (InterruptedException e)
            {
                System.out.println("Erro ao carregar módulo: " + e.getMessage());
            }
            finally
            {
                latch.countDown();
            }
        }
    }
}
