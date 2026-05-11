import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PasswordAnalyzer analyzer = new PasswordAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Password Analyzer ===");
        System.out.print("Enter a password to analyze: ");
        String password = scanner.nextLine();

        System.out.println(analyzer.analyze(password));
    }
}