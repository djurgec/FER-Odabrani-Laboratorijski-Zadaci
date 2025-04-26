
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class SintaksniAnalizator {


    public static void akcija1(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }

        nezavrsni_znakovi.push("<lista_naredbi>");
        razmak.push(stog_max + 1);
    }

    public static void akcija2(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<lista_naredbi>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<naredba>");
        razmak.push(stog_max + 1);
    }

    public static void akcija3(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
            for (int i = 0; i < limit + 1; i++) {
                output.append(" ");
            }
            output.append("$").append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
    }

    public static void akcija4(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<naredba_pridruzivanja>");
        razmak.push(stog_max + 1);
    }

    public static void akcija5(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<za_petlja>");
        razmak.push(stog_max + 1);
    }

    public static String akcija6(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<E>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("OP_PRIDRUZI");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static String akcija7(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        for (int i = 0; i < limit; i++) {
            output.append(" ");
        }
        output.append(nezavrsni_znakovi.pop()).append("\n");
        nezavrsni_znakovi.push("KR_AZ");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<lista_naredbi>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<E>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("KR_DO");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<E>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("KR_OD");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("IDN");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static void akcija8(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<E_lista>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<T>");
        razmak.push(stog_max + 1);
    }

    public static String akcija9(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<E>");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static void akcija10(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak) {
        int stog_max = razmak.peek();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            int limit = razmak.pop();
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<T_lista>");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<P>");
        razmak.push(stog_max + 1);
    }

    public static String akcija11(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<T>");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static String akcija12(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("<P>");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static String akcija13(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            nezavrsni_znakovi.pop();
        }
        nezavrsni_znakovi.push("D_ZAGRADA");
        razmak.push(stog_max + 1);
        nezavrsni_znakovi.push("<E>");
        razmak.push(stog_max + 1);
        for (int i = 0; i < limit + 1; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }

    public static String akcija14(Stack<String> nezavrsni_znakovi, StringBuilder output, Stack<Integer> razmak, String naredba, Scanner scanner) {
        int stog_max = razmak.peek();
        int limit = razmak.pop();
        int dodatak;
        if (nezavrsni_znakovi.peek().startsWith("<")) {
            dodatak = 1;
            for (int i = 0; i < limit; i++) {
                output.append(" ");
            }
            output.append(nezavrsni_znakovi.pop()).append("\n");
        } else {
            dodatak = 0;
            nezavrsni_znakovi.pop();
        }
        for (int i = 0; i < limit + dodatak; i++) {
            output.append(" ");
        }
        output.append(naredba).append("\n");
        if (scanner.hasNext()) {
            return scanner.nextLine().trim();
        } else return "kraj";
    }


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        boolean greska = false;
        Stack<Integer> razmak = new Stack<Integer>();
        razmak.push(0);
        StringBuilder output = new StringBuilder();
        Stack<String> nezavrsni_znakovi = new Stack<>();
        nezavrsni_znakovi.push("<program>");
        String naredba = scanner.nextLine().trim();
        while (!nezavrsni_znakovi.empty()) {
            if (greska) break;
            switch (nezavrsni_znakovi.peek()) {
                case ("<program>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("KR_ZA") || naredba.equals("kraj")) {
                        akcija1(nezavrsni_znakovi, output, razmak);
                    } else {
                        System.out.println("err " + naredba);

                        greska = true;
                    }
                    break;

                case ("<lista_naredbi>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("KR_ZA")) {

                        akcija2(nezavrsni_znakovi, output, razmak);
                    } else if (naredba.startsWith("KR_AZ") || naredba.equals("kraj")) {

                        akcija3(nezavrsni_znakovi, output, razmak);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<naredba>"):
                    if (naredba.startsWith("IDN")) {

                        akcija4(nezavrsni_znakovi, output, razmak);
                    } else if (naredba.startsWith("KR_ZA")) {

                        akcija5(nezavrsni_znakovi, output, razmak);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<naredba_pridruzivanja>"):
                    if (naredba.startsWith("IDN")) {

                        naredba = akcija6(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<za_petlja>"):
                    if (naredba.startsWith("KR_ZA")) {

                        naredba = akcija7(nezavrsni_znakovi, output, razmak, naredba, scanner);

                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<E>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("BROJ") || naredba.startsWith("OP_PLUS") || naredba.startsWith("OP_MINUS") || naredba.startsWith("L_ZAGRADA")) {

                        akcija8(nezavrsni_znakovi, output, razmak);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<E_lista>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("D_ZAGRADA") || naredba.startsWith("KR_ZA") || naredba.startsWith("KR_DO") || naredba.startsWith("KR_AZ") || naredba.equals("kraj")) {

                        akcija3(nezavrsni_znakovi, output, razmak);
                    } else if (naredba.startsWith("OP_PLUS") || naredba.startsWith("OP_MINUS")) {

                        naredba = akcija9(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<T>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("BROJ") || naredba.startsWith("OP_PLUS") || naredba.startsWith("OP_MINUS") || naredba.startsWith("L_ZAGRADA")) {

                        akcija10(nezavrsni_znakovi, output, razmak);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<T_lista>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("OP_PLUS") || naredba.startsWith("OP_MINUS") || naredba.startsWith("D_ZAGRADA") || naredba.startsWith("KR_ZA") || naredba.startsWith("KR_DO") || naredba.startsWith("KR_AZ") || naredba.equals("kraj")) {

                        akcija3(nezavrsni_znakovi, output, razmak);
                    } else if (naredba.startsWith("OP_PUTA") || naredba.startsWith("OP_DIJELI")) {

                        naredba = akcija11(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("<P>"):
                    if (naredba.startsWith("IDN") || naredba.startsWith("BROJ")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else if (naredba.startsWith("OP_PLUS") || naredba.startsWith("OP_MINUS")) {

                        naredba = akcija12(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else if (naredba.startsWith("L_ZAGRADA")) {

                        naredba = akcija13(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("IDN"):
                    if (naredba.startsWith("IDN")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("OP_PRIDRUZI"):
                    if (naredba.startsWith("OP_PRIDRUZI")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("KR_OD"):
                    if (naredba.startsWith("KR_OD")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("KR_DO"):
                    if (naredba.startsWith("KR_DO")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("KR_AZ"):
                    if (naredba.startsWith("KR_AZ")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                case ("D_ZAGRADA"):
                    if (naredba.startsWith("D_ZAGRADA")) {

                        naredba = akcija14(nezavrsni_znakovi, output, razmak, naredba, scanner);
                    } else {
                        System.out.println("err " + naredba);
                        greska = true;
                    }
                    break;

                default:
                    greska = true;

            }
        }
        if (!greska) System.out.println(output.toString().trim());
    }
}