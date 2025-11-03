import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Assento {
    private final String codigo;
    private final ReentrantLock trava;
    private final AtomicBoolean estaOcupado;
    private volatile String nomePassageiro;
    private volatile Categoria categoria;
    private volatile String codigoReserva;
    
    public Assento(String codigo) {
        this.codigo = codigo;
        this.trava = new ReentrantLock(true);
        this.estaOcupado = new AtomicBoolean(false);
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public ReentrantLock getTrava() {
        return trava;
    }
    
    public AtomicBoolean getFlagOcupado() {
        return estaOcupado;
    }
    
    public boolean estaOcupado() {
        return estaOcupado.get();
    }
    
    public void ocupar(String nomePassageiro, Categoria categoria, String codigoReserva) {
        this.nomePassageiro = nomePassageiro;
        this.categoria = categoria;
        this.codigoReserva = codigoReserva;
        this.estaOcupado.set(true);
    }
    
    public void liberar() {
        this.nomePassageiro = null;
        this.categoria = null;
        this.codigoReserva = null;
        this.estaOcupado.set(false);
    }
    
    public String getNomePassageiro() {
        return nomePassageiro;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public String getCodigoReserva() {
        return codigoReserva;
    }
}