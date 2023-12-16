public class reteNomeDimensione {
    public String nome;
    private int dimensione;

    public reteNomeDimensione(String s) {
        String[] token = s.split("-");
        this.nome = token[0];
        this.dimensione = Integer.parseInt(token[1]);
    }

    public int getDimensione() {
        return dimensione;
    }

    public String getNome() {
        return nome;
    }
}
