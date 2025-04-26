import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class SimPa {

    public static boolean pronadiJednakost (String string, String[] polje) {
        for (String s : polje) {
            if (s.equals(string)) return true;
        }
        return false;
    }

    public static boolean zadnjiUNizu (String string, String[] polje) {
        for (int i = 0; i < polje.length; i++) {
            if (i== polje.length-1 && polje[i]==string) return true;
        }
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] ulazi = scanner.nextLine().split("\\|");
        String[] stanja = scanner.nextLine().split(",");
        String[] abeceda = scanner.nextLine().split(",");
        String[] stanja_stoga = scanner.nextLine().split(",");
        String[] prihvatljiva_st = scanner.nextLine().split(",");
        String trenutno_st = scanner.nextLine();
        String stanje_stoga = scanner.nextLine();
        Map<String, String> funkcije = new HashMap<>();
        while (scanner.hasNext()) {
            String[] djelovi = scanner.nextLine().split("->");
            funkcije.put(djelovi[0], djelovi[1]);
        }
        String pocetno_st = trenutno_st;
        String pocetni_stog = stanje_stoga;
        String ukupna_stanja_stoga = stanje_stoga;
        boolean stanje_ispravno = false, prihvaca = false, promijenio_stanje=false;
        StringBuilder rez = new StringBuilder();
        for (String ulaz : ulazi) {
            rez.append(trenutno_st).append("#").append(stanje_stoga).append("|");
            String [] dolazni_znakovi =  ulaz.split(",");
            for (String dolazni_znak : dolazni_znakovi) {
                String config = trenutno_st + "," + dolazni_znak + "," + stanje_stoga;
                prihvaca = false;
                stanje_ispravno = false;
                do{
                    promijenio_stanje=false;
                    for (String kljuc : funkcije.keySet()) {
                        if (kljuc.equals(trenutno_st+",$,"+stanje_stoga)) {
                            promijenio_stanje=true;
                            stanje_ispravno = true;
                            String prijelaz = funkcije.get(kljuc);
                            trenutno_st = prijelaz.split(",")[0];
                            if (prijelaz.split(",")[1].length()==1) {
                                if (prijelaz.split(",")[1].equals("$")){
                                    if (ukupna_stanja_stoga.length()>1) {
                                        ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                    }
                                    else ukupna_stanja_stoga="$";
                                }
                                else{
                                    ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                    ukupna_stanja_stoga=prijelaz.split(",")[1] + ukupna_stanja_stoga;
                                }
                                stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                            }
                            else {
                                ukupna_stanja_stoga = ukupna_stanja_stoga.substring(1);
                                ukupna_stanja_stoga = prijelaz.split(",")[1] + ukupna_stanja_stoga;
                                stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                            }
                            rez.append(trenutno_st).append("#").append(ukupna_stanja_stoga).append("|");
                            break;
                        }
                    }
                }while (promijenio_stanje);
                config = trenutno_st + "," + dolazni_znak + "," + stanje_stoga;
                stanje_ispravno=false;
                for (String kljuc : funkcije.keySet()) {
                    if (kljuc.equals(config)) {
                        stanje_ispravno = true;
                        String prijelaz = funkcije.get(kljuc);
                        trenutno_st = prijelaz.split(",")[0];
                        if (prijelaz.split(",")[1].length()==1) {
                            if (prijelaz.split(",")[1].equals("$")){
                                if (ukupna_stanja_stoga.length()>1) {
                                    ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                }
                                else ukupna_stanja_stoga="$";
                            }
                            else{
                                ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                ukupna_stanja_stoga=prijelaz.split(",")[1] + ukupna_stanja_stoga;
                            }
                            stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                        }
                        else {
                            ukupna_stanja_stoga = ukupna_stanja_stoga.substring(1);
                            ukupna_stanja_stoga = prijelaz.split(",")[1] + ukupna_stanja_stoga;
                            stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                        }
                        rez.append(trenutno_st).append("#").append(ukupna_stanja_stoga).append("|");
                        break;
                    }
                }

                promijenio_stanje=true;
                do{
                    promijenio_stanje=false;
                    for (String kljuc : funkcije.keySet()) {
                        if (kljuc.equals(trenutno_st+",$,"+stanje_stoga) && (!pronadiJednakost(trenutno_st, prihvatljiva_st) || dolazni_znak!=dolazni_znakovi[dolazni_znakovi.length-1]) ){
                            promijenio_stanje=true;
                            stanje_ispravno = true;
                            String prijelaz = funkcije.get(kljuc);
                            trenutno_st = prijelaz.split(",")[0];
                            if (prijelaz.split(",")[1].length()==1) {
                                if (prijelaz.split(",")[1].equals("$")){
                                    if (ukupna_stanja_stoga.length()>1) {
                                        ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                    }
                                    else ukupna_stanja_stoga="$";
                                }
                                else{
                                    ukupna_stanja_stoga=ukupna_stanja_stoga.substring(1);
                                    ukupna_stanja_stoga=prijelaz.split(",")[1] + ukupna_stanja_stoga;
                                }
                                stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                            }
                            else {
                                ukupna_stanja_stoga = ukupna_stanja_stoga.substring(1);
                                ukupna_stanja_stoga = prijelaz.split(",")[1] + ukupna_stanja_stoga;
                                stanje_stoga = ukupna_stanja_stoga.substring(0, 1);
                            }
                            rez.append(trenutno_st).append("#").append(ukupna_stanja_stoga).append("|");
                            break;
                        }
                    }
                }while (promijenio_stanje==true);
                if (!stanje_ispravno) break;

            }
            if (!stanje_ispravno) {
                rez.append("fail|0").append("\n");
            }
            else {
                for (String prihvatljivo_st : prihvatljiva_st) {
                    if (trenutno_st.equals(prihvatljivo_st)) {
                        prihvaca = true;
                        break;
                    }
                }
                if (prihvaca) {
                    rez.append("1").append("\n");
                }
                else rez.append("0").append("\n");
            }
            trenutno_st = pocetno_st;
            ukupna_stanja_stoga=pocetni_stog;
            stanje_stoga=pocetni_stog;
        }
        rez.deleteCharAt(rez.length()-1);
        System.out.println(rez);
    }
}