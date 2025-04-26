import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class SemantickiAnalizator {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> deklaracije = new HashMap<>();
        boolean pronaden_idn = false;
        StringBuilder izlaz = new StringBuilder();
        Stack<Map.Entry<String, Integer>> deklaracije_petlje = new Stack<>();
        StringBuilder lex = new StringBuilder();
        int dubina_petlje = 0;
        while (scanner.hasNextLine()) {
            String linija = scanner.nextLine().trim();
           if (!linija.startsWith("<") && !linija.startsWith("$")) {
               lex.append(linija + "\n");
           }
        }
        Scanner lex_linije = new Scanner(String.valueOf(lex));
        String trenutna_linija = lex_linije.nextLine().trim();

        while (trenutna_linija != null) {
            String sljedeca_linija = lex_linije.hasNextLine() ? lex_linije.nextLine().trim() : null;
            pronaden_idn = false;

            if (trenutna_linija.startsWith("KR_AZ")) {
                deklaracije_petlje.pop();
            } else if (trenutna_linija.startsWith("IDN") && sljedeca_linija != null && sljedeca_linija.startsWith("OP_PRIDRUZI") && !deklaracije.containsKey(trenutna_linija.split(" ")[2])) {
                deklaracije.put(trenutna_linija.split(" ")[2], Integer.valueOf(trenutna_linija.split(" ")[1]));
            } else if (trenutna_linija.startsWith("IDN") && sljedeca_linija != null && sljedeca_linija.startsWith("KR_OD")) {
                AbstractMap.SimpleEntry<String, Integer> deklaracija = new AbstractMap.SimpleEntry<>(
                        trenutna_linija.split(" ")[2],
                        Integer.valueOf(trenutna_linija.split(" ")[1])
                );
                deklaracije_petlje.push(deklaracija);
            } else if (trenutna_linija.startsWith("IDN") && (sljedeca_linija == null || !sljedeca_linija.startsWith("OP_PRIDRUZI"))) {
                if (!deklaracije_petlje.isEmpty()) {
                    Stack<Map.Entry<String, Integer>> pomocni = new Stack<>();
                    while (!deklaracije_petlje.isEmpty()) {
                        if (deklaracije_petlje.peek().getKey().equals(trenutna_linija.split(" ")[2])) {
                            if (deklaracije_petlje.peek().getValue() == Integer.valueOf(trenutna_linija.split(" ")[1])) {
                                izlaz.append("err " + trenutna_linija.split(" ")[1] + " " + trenutna_linija.split(" ")[2] + "\n");
                                System.out.println(izlaz);
                                System.exit(0);
                            }
                            pronaden_idn=true;
                            izlaz.append(trenutna_linija.split(" ")[1] + " " + deklaracije_petlje.peek().getValue() + " " + trenutna_linija.split(" ")[2] + "\n");
                        }
                        pomocni.push(deklaracije_petlje.pop());
                    }
                    while (!pomocni.isEmpty()) {
                        deklaracije_petlje.push(pomocni.pop());
                    }

                }
                if (deklaracije.containsKey(trenutna_linija.split(" ")[2]) && !pronaden_idn) {
                    if (deklaracije.get(trenutna_linija.split(" ")[2]).equals(Integer.valueOf(trenutna_linija.split(" ")[1]))) {
                        izlaz.append("err " + trenutna_linija.split(" ")[1] + " " + trenutna_linija.split(" ")[2] + "\n");
                        System.out.println(izlaz);
                        System.exit(0);
                    }
                    izlaz.append(trenutna_linija.split(" ")[1] + " " + deklaracije.get(trenutna_linija.split(" ")[2]) + " " + trenutna_linija.split(" ")[2] + "\n");
                } else if (!pronaden_idn) {
                    izlaz.append("err " + trenutna_linija.split(" ")[1] + " " + trenutna_linija.split(" ")[2] + "\n");
                    System.out.println(izlaz);
                    System.exit(0);
                }
            }
            trenutna_linija = sljedeca_linija;
        }

        System.out.println(izlaz);
    }

}