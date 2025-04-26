import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;

public class Parser {

    public static void produkcijaC(Stack<String> stog) {
        System.out.print("A");
        produkcijaA(stog);
        System.out.print("A");
        produkcijaA(stog);
    }

    public static void produkcijaA(Stack<String> stog) {
        if (!stog.peek().equals("b") && !stog.peek().equals("a")) {
            System.out.println();
            System.out.print("NE");
            exit(0);
        } else if (stog.peek().equals("b")) {
            stog.pop();
            System.out.print("C");
            produkcijaC(stog);
        }
        else {
            stog.pop();
        }
    }

    public static void produkcijaB(Stack<String> stog) {
    if (stog.peek().equals("c")) {
        stog.pop();
        if (!stog.peek().equals("c")) {
            System.out.println();
            System.out.print("NE");
            exit(0);
        }
        stog.pop();
        System.out.print("S");
        produkcijaS(stog);
        if (!stog.peek().equals("b")) {
            System.out.println();
            System.out.print("NE");
            exit(0);
        }
        stog.pop();
        if (!stog.peek().equals("c")) {
            System.out.println();
            System.out.print("NE");
            exit(0);
        }
        stog.pop();
    }

    }

    public static void produkcijaS(Stack<String> stog) {
    if (!stog.peek().equals("a") && !stog.peek().equals("b")) {
        System.out.println();
        System.out.print("NE");
        exit(0);
    } else if (stog.peek().equals("a")) {
        stog.pop();
        System.out.print("A");
        produkcijaA(stog);
        System.out.print("B");
        produkcijaB(stog);
    }
    else{
        stog.pop();
        System.out.print("B");
        produkcijaB(stog);
        System.out.print("A");
        produkcijaA(stog);
    }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String ulaz = scanner.nextLine();
        Stack<String> stog = new Stack<>();
        stog.push("zavrsni");
        for (int i = ulaz.length()-1; i >= 0 ; i--) {
            stog.push(String.valueOf(ulaz.charAt(i)));
        }
        System.out.print("S");
        produkcijaS(stog);

        if (stog.pop().equals("zavrsni")) {
            System.out.println();
            System.out.print("DA");
        }
        else{
            System.out.println();
            System.out.print("NE");
        }
}
}