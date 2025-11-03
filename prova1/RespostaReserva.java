public class RespostaReserva {
    private final boolean sucesso;
    private final String codigoAssento;
    private final String codigoReserva;
    private final String codigoErro;
    private final String mensagemErro;
    
    private RespostaReserva(boolean sucesso, String codigoAssento, String codigoReserva, String codigoErro, String mensagemErro) {
        this.sucesso = sucesso;
        this.codigoAssento = codigoAssento;
        this.codigoReserva = codigoReserva;
        this.codigoErro = codigoErro;
        this.mensagemErro = mensagemErro;
    }
    
    public static RespostaReserva sucesso(String codigoAssento, String codigoReserva) {
        return new RespostaReserva(true, codigoAssento, codigoReserva, null, null);
    }
    
    public static RespostaReserva falha(String codigoErro, String mensagemErro) {
        return new RespostaReserva(false, null, null, codigoErro, mensagemErro);
    }
    
    public boolean getSucesso() { 
        return sucesso; 
    }
    public String getCodigoAssento() { 
        return codigoAssento; 
    }
    public String getCodigoReserva() { 
        return codigoReserva; 
    }
    public String getCodigoErro() { 
        return codigoErro; 
    }
    public String getMensagemErro() { 
        return mensagemErro; 
    }
}