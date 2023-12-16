import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class calcoloParametri {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("E/R? + indirizzo");
        String str = s.nextLine();
        String[] token = str.split(" ");
        String eur = token[0];
        String indirizzo = token[1];

        ArrayList<reteNomeDimensione> l = new ArrayList<>();
        System.out.println("Inserisci nome reti e dimensione con separatore '-' [esempio: K-24] (alla fine ctrl-D)");
        while(s.hasNextLine()) {
            l.add(new reteNomeDimensione(s.nextLine()));
        }
        if (eur.equals("E")) l.sort(Comparator.comparing(reteNomeDimensione::getDimensione).reversed());
        indirizzo ind = new indirizzo(indirizzo);
        for (reteNomeDimensione i : l) {
            System.out.println("_______________________");
            ind.aggiungiSubBit(i.getDimensione());
            if (eur.equals("R")) ind.aggiustaIndirizzoRegola();
            System.out.println( "Rete " + i.getNome() + ", numero dispositivi: " +
                                Integer.toString(i.getDimensione()) + " -> " +
                                ind.calcoloDispositiviMassimi(i.getDimensione()) + " -> " +
                                ind.calcoloBit(i.getDimensione()) + " bit necessari");
            ind.calcolaParametri();
            ind = ind.indirizzoSuccessivo();
        }
    }
}