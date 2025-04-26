import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MinDka {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] stanja = scanner.nextLine().split(",");
        String[] abeceda = scanner.nextLine().split(",");
        String[] prihvatljiva_st = scanner.nextLine().split(",");
        String pocetno_st = scanner.nextLine();
        Map<String, String> funkcije = new HashMap<>();
        while (scanner.hasNext()) {
            String[] djelovi = scanner.nextLine().split("->");
            funkcije.put(djelovi[0], djelovi[1]);

        }
        LinkedList<String> nova_lista_stanja = new LinkedList<>();
        nova_lista_stanja.add(pocetno_st);
        LinkedList<String> dohvatljiva_stanja = new LinkedList<>();
        while (!nova_lista_stanja.isEmpty()) {
            String stanje = nova_lista_stanja.removeFirst();
            for (Map.Entry<String, String> entry : funkcije.entrySet()) {
                if (entry.getKey().split(",")[0].equals(stanje) &&
                        !nova_lista_stanja.contains(entry.getValue()) &&
                        !dohvatljiva_stanja.contains(entry.getValue())) {
                    nova_lista_stanja.add(entry.getValue());
                }
            }
            dohvatljiva_stanja.add(stanje);
            nova_lista_stanja.remove(stanje);
        }
        Map<String, String> nove_funkcije = new HashMap<>(funkcije);
        for (Map.Entry<String, String> entry : funkcije.entrySet()) {
            String stanje = entry.getKey().split(",")[0];
            if (Arrays.asList(stanja).contains(stanje) && !dohvatljiva_stanja.contains(stanje)) {
                for (String simbol : abeceda) {
                    nove_funkcije.remove(stanje + "," + simbol);
                }
            }
        }
        Set<String> nepotrebna_stanja = new HashSet<>();
        for (String s : stanja) {
            if (!dohvatljiva_stanja.contains(s))
                nepotrebna_stanja.add(s);
        }
        Collections.sort(dohvatljiva_stanja);
        ArrayList<String> parovi_stanja = new ArrayList<>();
        int i = 0;
        for (String stanje1 : dohvatljiva_stanja) {
            for (String stanje2 : dohvatljiva_stanja) {
                if (stanje1.compareTo(stanje2)<0) {
                    parovi_stanja.add(stanje1 + "," + stanje2);
                }
            }
        }
        LinkedList<String> lista_neistovjetnih = new LinkedList<>();
        LinkedList<String> lista_istovjetnih = new LinkedList<>();
        for (String par : parovi_stanja) {
            String stanje1 = par.split(",")[0];
            String stanje2 = par.split(",")[1];
            if ((Arrays.asList(prihvatljiva_st).contains(stanje1) &&
                    !Arrays.asList(prihvatljiva_st).contains(stanje2)) ||
                    (!Arrays.asList(prihvatljiva_st).contains(stanje1) &&
                            Arrays.asList(prihvatljiva_st).contains(stanje2))) {
                lista_neistovjetnih.add(par);
            } else {
                for (String simbol : abeceda) {
                    String prijelaz1 = nove_funkcije.get(stanje1 + "," + simbol);
                    String prijelaz2 = nove_funkcije.get(stanje2 + "," + simbol);
                    if ((Arrays.asList(prihvatljiva_st).contains(prijelaz1) &&
                            !Arrays.asList(prihvatljiva_st).contains(prijelaz2)) ||
                            (!Arrays.asList(prihvatljiva_st).contains(prijelaz1) &&
                                    Arrays.asList(prihvatljiva_st).contains(prijelaz2))) {
                        lista_neistovjetnih.add(par);
                        break;
                    }
                }
            }
        }

        for (String par : parovi_stanja) {
            if (!lista_neistovjetnih.contains(par))
                lista_istovjetnih.add(par);
        }

        boolean dodao_novi_par;
        do {
            dodao_novi_par = false;
            for (String par_istovjetnih : lista_istovjetnih) {
                if (!lista_neistovjetnih.contains(par_istovjetnih)) {
                    String stanje1 = par_istovjetnih.split(",")[0];
                    String stanje2 = par_istovjetnih.split(",")[1];
                    for (String simbol : abeceda) {
                        String prijelaz1 = nove_funkcije.get(stanje1 + "," + simbol);
                        String prijelaz2 = nove_funkcije.get(stanje2 + "," + simbol);
                        if (prijelaz1.compareTo(prijelaz2)>0) {
                            String privremeni=prijelaz1;
                            prijelaz1=prijelaz2;
                            prijelaz2=privremeni;
                        }
                        if (lista_neistovjetnih.contains(prijelaz1 + "," + prijelaz2)) {
                            lista_neistovjetnih.add(par_istovjetnih);
                            dodao_novi_par = true;
                            break; // Prekida unutarnju petlju jer je par promijenjen
                        }
                    }
                }
            }
        } while (dodao_novi_par);
        LinkedList<String> nova_lista_istovjetnih = new LinkedList<String>(lista_istovjetnih);
        nova_lista_istovjetnih.removeIf(par -> lista_istovjetnih.contains(par) && lista_neistovjetnih.contains(par));
        Collections.sort(nova_lista_istovjetnih);
        Map<String, String> funkcije_3 = new HashMap<>(nove_funkcije);

;
        for (String par : nova_lista_istovjetnih) {
            String stanje1 = par.split(",")[0];
            String stanje2 = par.split(",")[1];
            String veceStanje = null;
            if (stanje1.compareTo(stanje2) < 0)
                veceStanje=stanje2;
            else veceStanje=stanje1;
            nepotrebna_stanja.add(veceStanje);
        }
        for (String s : nepotrebna_stanja) {
            for (String simbol : abeceda) {
                funkcije_3.remove(s + "," + simbol);
            }
        }
        Map<String, String> funkcije_final = new HashMap<>(funkcije_3);
        for (Map.Entry<String, String> entry : funkcije_3.entrySet()) {
            String s = entry.getValue();
            if (nepotrebna_stanja.contains(s)) {
                for (String par : nova_lista_istovjetnih) {
                    if (par.split(",")[1].equals(s)) {
                        s=par.split(",")[0];
                    }
                }
            }
            funkcije_final.put(entry.getKey(), s);
        }

        List<Map.Entry<String, String>> entry_list = new ArrayList<>(funkcije_final.entrySet());
        entry_list.sort(Map.Entry.comparingByKey());
        ArrayList<String> stanja_ispis = new ArrayList<>();
        ArrayList<String> prihvatljiva_stanja_ispis = new ArrayList<>();

        if (nepotrebna_stanja.contains(pocetno_st)) {
            for (String s : lista_istovjetnih) {
                if (pocetno_st.equals(s.split(",")[1]))
                    pocetno_st=s.split(",")[0];
            }
        }

        for (String s : stanja) {
            if (!nepotrebna_stanja.contains(s))
                stanja_ispis.add(s);
        }
        for (String s : prihvatljiva_st) {
            if (!nepotrebna_stanja.contains(s))
                prihvatljiva_stanja_ispis.add(s);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : stanja_ispis) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");
        for (String s : abeceda) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");
        if (prihvatljiva_stanja_ispis.isEmpty()) sb.append("\n");
        for (String s : prihvatljiva_stanja_ispis) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");
        sb.append(pocetno_st).append("\n");
        for (Map.Entry<String, String> entry : entry_list) {
            sb.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
        }
        System.out.print(sb);
    }
}
