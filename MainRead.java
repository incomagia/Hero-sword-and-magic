import java.util.Scanner;

public class MainRead {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hero and Magic!");
        System.out.println("Type 'help' for a list of commands. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Goodbye!");
                break;
            }

            CommandProcessor.processCommand(input);
        }

        scanner.close();
    }
}
