import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeksickiAnalizator {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int brojReda = 1, index=0;
        boolean jeBroj = true, jeRijec = true;
        while (scanner.hasNextLine()) {
            String[] jedinke = scanner.nextLine().trim().split(" ");

            ArrayList<String> jedinke_trimmed = new ArrayList<String>();
            for (int i = 0; i < jedinke.length; i++) {
                if (jedinke[i].startsWith("//")) {
                    break;
                }
                if (!jedinke[i].equals("")) {
                    jedinke_trimmed.add(jedinke[i]);
                }
            }

            for (String jedinka : jedinke_trimmed) {
                switch (jedinka) {
                    case "=":
                        System.out.println("OP_PRIDRUZI " + brojReda + " " + jedinka);
                        break;
                    case "+":
                        System.out.println("OP_PLUS " + brojReda + " " + jedinka);
                        break;
                    case "-":
                        System.out.println("OP_MINUS " + brojReda + " " + jedinka);
                        break;
                    case "*":
                        System.out.println("OP_PUTA " + brojReda + " " + jedinka);
                        break;
                    case "/":
                        System.out.println("OP_DIJELI " + brojReda + " " + jedinka);
                        break;
                    case "(":
                        System.out.println("L_ZAGRADA " + brojReda + " " + jedinka);
                        break;
                    case ")":
                        System.out.println("D_ZAGRADA " + brojReda + " " + jedinka);
                        break;
                    case "za":
                        System.out.println("KR_ZA " + brojReda + " " + jedinka);
                        break;
                    case "az":
                        System.out.println("KR_AZ " + brojReda + " " + jedinka);
                        break;
                    case "od":
                        System.out.println("KR_OD " + brojReda + " " + jedinka);
                        break;
                    case "do":
                        System.out.println("KR_DO " + brojReda + " " + jedinka);
                        break;
                    default:
                        jeRijec = true;
                        jeBroj=true;
                        for (char ch : jedinka.toCharArray()) {
                            if ((Character.isLetter(jedinka.toCharArray()[0]) && !Character.isLetterOrDigit(ch)) || !Character.isLetter(jedinka.toCharArray()[0])) {
                                jeRijec=false;
                            }
                            if (!Character.isDigit(ch)) {
                                jeBroj = false;
                            }
                        }
                        if (jeRijec) {
                            System.out.println("IDN " + brojReda + " " + jedinka);
                            break;
                        } else if (jeBroj) {
                            System.out.println("BROJ " + brojReda + " " + jedinka);
                            break;
                        }
                        else {
                            index=1;
                            boolean[] iskoristeni = new boolean[jedinka.toCharArray().length];
                            int i = 0;
                            for (char ch : jedinka.toCharArray()) {
                                if (i==jedinka.toCharArray().length || iskoristeni[i]){
                                    i++;
                                    if (index<=i) index=i+1;
                                    continue;
                                }
                                iskoristeni[i] = true;
                                if (!Character.isLetterOrDigit(ch)) {
                                    switch (String.valueOf(ch)) {
                                        case "=":
                                            System.out.println("OP_PRIDRUZI " + brojReda + " " + ch);
                                            break;
                                        case "+":
                                            System.out.println("OP_PLUS " + brojReda + " " + ch);
                                            break;
                                        case "-":
                                            System.out.println("OP_MINUS " + brojReda + " " + ch);
                                            break;
                                        case "*":
                                            System.out.println("OP_PUTA " + brojReda + " " + ch);
                                            break;
                                        case "/":
                                            System.out.println("OP_DIJELI " + brojReda + " " + ch);
                                            break;
                                        case "(":
                                            System.out.println("L_ZAGRADA " + brojReda + " " + ch);
                                            break;
                                        case ")":
                                            System.out.println("D_ZAGRADA " + brojReda + " " + ch);
                                            break;
                                    }
                                } else if (Character.isLetter(ch)) {
                                    StringBuilder tocna_jedinka = new StringBuilder(String.valueOf(ch));
                                    while (index<jedinka.toCharArray().length && Character.isLetterOrDigit(jedinka.toCharArray()[index])) {
                                            iskoristeni[index] = true;
                                            tocna_jedinka.append(String.valueOf(jedinka.toCharArray()[index]));
                                            index++;

                                    }
                                    System.out.println("IDN " + brojReda + " " + tocna_jedinka);


                            }
                                 else {
                                    StringBuilder tocna_jedinka = new StringBuilder(String.valueOf(ch));
                                    while (index<jedinka.toCharArray().length && Character.isDigit(jedinka.toCharArray()[index])) {
                                            iskoristeni[index] = true;
                                        tocna_jedinka.append(String.valueOf(jedinka.toCharArray()[index]));
                                            index++;

                                    }
                                    System.out.println("BROJ " + brojReda + " " + tocna_jedinka);
                                }
                                i++;
                                if (index<=i) index=i+1;
                            }
                        }
                }

            }
            brojReda++;
        }
    }
}