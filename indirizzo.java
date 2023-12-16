public class indirizzo {
    private int o1;
    private int o2;
    private int o3;
    private int o4;
    private int netIdSz;
    private int netSubIdSz;
    private int dispositivi;

    public indirizzo(String s) {
        // tipo 127.0.0.1/28
        String[] token = s.split("\\.");
        o1 = Integer.parseInt(token[0]);
        o2 = Integer.parseInt(token[1]);
        o3 = Integer.parseInt(token[2]);
        token = token[3].split("/");
        o4 = Integer.parseInt(token[0]);
        netIdSz = Integer.parseInt(token[1]);
    }

    public void aggiungiSubBit(int numDispositivi) {
        dispositivi = numDispositivi;
        int bit = calcoloBit(numDispositivi);
        netSubIdSz = 32 - bit;
    }

    public int calcoloBit(int numDispositivi) {
        int base2 = 1;
        int bit = 0;
        while(base2 < numDispositivi) {
            base2 *= 2;
            bit++;
        }
        return bit;
    }

    public int calcoloDispositiviMassimi(int numDispositivi) {
        int base2 = 1;
        while(base2 < numDispositivi) base2 *= 2;
        return base2;
    }

    private static int BinaryToInt(String s) {
        int p = 1;
        int sol = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            sol += Character.getNumericValue(s.charAt(i)) * p;
            p *= 2;
        }
        return sol;
    }
    private static String IntToBinary(int n) {
        StringBuilder sb = new StringBuilder();
        int count = 8;
        while(count != 0) {
            sb.append(n%2);
            n /= 2;
            count--;
        }
        return sb.reverse().toString();
    }

    private static String indirizzoDaStringaBinaria(String s) {
        return  BinaryToInt(s.substring(0, 8)) + "." +
                BinaryToInt(s.substring(8, 16)) + "." +
                BinaryToInt(s.substring(16, 24)) + "." +
                BinaryToInt(s.substring(24, 32));
    }

    public void calcolaParametri() {
        System.out.printf("Indirizzo base: \t%s\n", base());
        System.out.printf("Indirizzo broadcast: \t%s\n", broadcast());
        System.out.printf("Indirizzo gateway: \t%s\n", gateway());
        System.out.printf("Indirizzo primo IP: \t%s\n", primo());
        System.out.printf("Indirizzo ultimo IP: \t%s\n", ultimo());
        System.out.printf("Indirizzo netmask: \t%s\n", netmask());
        System.out.printf("Indirizzo wildcard: \t%s\n", wildcard());

    }

    public String wildcard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < netSubIdSz; i++) sb.append('0');
        for (int i = netSubIdSz; i < 32; i++) sb.append('1'); 
        return indirizzoDaStringaBinaria(sb.toString()); 
    }

    public String netmask() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < netSubIdSz; i++) sb.append('1');
        for (int i = netSubIdSz; i < 32; i++) sb.append('0'); 
        return indirizzoDaStringaBinaria(sb.toString()); 
    }

    public String ultimo() {
        StringBuilder sb = creaBuilder();

        for (int i = netSubIdSz; i < sb.length(); i++) sb.setCharAt(i, '1');
        for (int i = 31; i >= 0; i--) {
            if ((sb.charAt(i) == '1')) {
                sb.setCharAt(i, '0');
                for (int j = i + 1; j < sb.length(); j++) sb.setCharAt(j, '1');
                break;
            }
        }

        for (int i = 31; i >= 0; i--) {
            if ((sb.charAt(i) == '1')) {
                sb.setCharAt(i, '0');
                for (int j = i + 1; j < sb.length(); j++) sb.setCharAt(j, '1');
                break;
            }
        }

        return indirizzoDaStringaBinaria(sb.toString());
    }

    public String primo() {
        StringBuilder sb = creaBuilder();

        for (int i = 31; i >= 0; i--) {
            if (sb.charAt(i) == '0') {
                sb.setCharAt(i, '1');
                break;
            } else {
                sb.setCharAt(i, '0');
            }
        }

        return indirizzoDaStringaBinaria(sb.toString());
    }

    public String gateway() {
        StringBuilder sb = creaBuilder();

        for (int i = netSubIdSz; i < sb.length() - 1; i++) sb.setCharAt(i, '1');
        sb.setCharAt(31, '0');

        return indirizzoDaStringaBinaria(sb.toString());
    }

    public String broadcast() {
        StringBuilder sb = creaBuilder();

        for (int i = netSubIdSz; i < sb.length(); i++) sb.setCharAt(i, '1');

        return indirizzoDaStringaBinaria(sb.toString());
    }

    public String base() {
        return  Integer.toString(o1) + "." +
                Integer.toString(o2) + "." + 
                Integer.toString(o3) + "." +
                Integer.toString(o4) + "/" + 
                Integer.toString(netSubIdSz);
    }

    public indirizzo indirizzoSuccessivo() {
        StringBuilder sb = creaBuilder();

        for (int i = netSubIdSz; i < sb.length(); i++) sb.setCharAt(i, '1');

        // broadcast + 1 (euristica)
        for (int i = 31; i >= 0; i--) {
            if (sb.charAt(i) == '0') {
                sb.setCharAt(i, '1');
                break;
            } else {
                sb.setCharAt(i, '0');
            }
        }

        return new indirizzo(indirizzoDaStringaBinaria(sb.toString()) + "/" + Integer.toString(netIdSz));
    }

    // aumento il quarto ottetto finchè non trovo un valore divisibile per il numero massimo di dispositivi
    public void aggiustaIndirizzoRegola() {
        if (this.o4 == 0) return;
        if (this.o4 % calcoloDispositiviMassimi(dispositivi) == 0) return;
        int vecchio_valore = this.o4;
        while (this.o4 % calcoloDispositiviMassimi(dispositivi) != 0 && this.o4 < 256) this.o4++;
        if (this.o4 == 256) {
            o4 = 0;
            o3++;
        }
        //System.out.printf("Regola! Da %d a %d in quanto %d è divisibile per %d\n", vecchio_valore, o4, o4, calcoloDispositiviMassimi(dispositivi));
    }

    private StringBuilder creaBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(IntToBinary(o1));
        sb.append(IntToBinary(o2));
        sb.append(IntToBinary(o3));
        sb.append(IntToBinary(o4));
        return sb;
    }
}