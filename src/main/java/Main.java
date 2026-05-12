import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PasswordAnalyzer analyzer = new PasswordAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("      🔐 Password Analyzer      ");
        System.out.println("   Type 'exit' to quit          ");
        System.out.println("================================");

        while (true) {
            System.out.print("\nEnter a password to analyze: ");
            String password = scanner.nextLine().trim();

            if (password.equalsIgnoreCase("exit")) {
                System.out.println("\nStay secure! Goodbye 👋");
                break;
            }

            if (password.isEmpty()) {
                System.out.println("⚠️  Please enter a password!");
                continue;
            }

            System.out.println(analyzer.analyze(password));
            System.out.println("\n─────────────────────────────");
        }

        scanner.close();

    }
}