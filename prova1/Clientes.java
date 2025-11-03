import java.util.*;

public class Clientes {
    public ArrayList<String> getClientes(int numeroClientes) {
        String[] primeirosNomes = {
            "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Igor", "Julia",
            "Karen", "Lucas", "Mariana", "Nicolas", "Olivia", "Paulo", "Renata", "Samuel", "Tatiana", "Vinicius"
     };

        String[] sobrenomes = {
            "Almeida", "Barbosa", "Carvalho", "Dias", "Esteves", "Ferreira", "Gomes", "Hernandes", "Ibrahim", "Jesus",
            "Klein", "Lima", "Macedo", "Nascimento", "Oliveira", "Pereira", "Queiroz", "Ramos", "Silva", "Teixeira"
        };

        Random random = new Random();

        ArrayList<String> nomesGerados = new ArrayList<>();

        while (nomesGerados.size() < numeroClientes) {
            String nome = primeirosNomes[random.nextInt(primeirosNomes.length)] + " " + sobrenomes[random.nextInt(sobrenomes.length)];
            nomesGerados.add(nome);
        }
        return nomesGerados;
    }
}
