public enum Categoria {
    PLATINUM(1),
    GOLD(2),
    SILVER(3),
    BASIC(4);
    
    private final int prioridade;
    
    Categoria(int prioridade) {
        this.prioridade = prioridade;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
}