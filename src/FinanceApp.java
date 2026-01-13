import java.util.Scanner;

public class FinanceApp {
    private static Store store = Store.loadFromFile();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("Welcome to the Finance Manager!");

        while (true) {
            System.out.println("Choose an option:\n1. Login\n2. Register\n3. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    registerUser();
                    break;
                case "3":
                    store.saveToFile();
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Unknown option.");
            }

            if (currentUser != null) {
                break;
            }
        }

        while (true) {
            showMainMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    addIncome();
                    break;
                case "2":
                    addExpense();
                    break;
                case "3":
                    setCategoryLimit();
                    break;
                case "4":
                    walletMenu();
                    break;
                case "5":
                    store.saveToFile();
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Unknown command.");
            }

            store.saveToFile();
        }
    }

    private static void showMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. Set Category Limit");
        System.out.println("4. Wallet");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = store.getUser(username);

        if (currentUser == null || !currentUser.getPassword().equals(password)) {
            System.out.println("Invalid username or password.");
        } else {
            System.out.println("Welcome " + currentUser.getUsername());
        }
    }

    private static void registerUser() {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();
        if (store.getUser(username) != null) {
            System.out.println("User with this username already exists.");
            return;
        }

        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        currentUser = new User(username, password);
        store.addUser(currentUser);

        System.out.println("Registration successful! You are now logged in.");
    }

    private static void addIncome() {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());

        store.getWallet(currentUser.getWalletId()).addTransaction(new Transaction(category, amount, true));

        System.out.println("Income added successfully!");
    }

    private static void addExpense() {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());

        store.getWallet(currentUser.getWalletId()).addTransaction(new Transaction(category, amount, false));

        System.out.println("Expense added successfully!");
    }

    private static void setCategoryLimit() {
        System.out.println("Enter the category name to set the limit:");
        String categoryName = scanner.nextLine();
        System.out.println("Enter the limit for the category:");
        double limit = Double.parseDouble(scanner.nextLine());

        store.getWallet(currentUser.getWalletId()).setCategoryLimit(categoryName, limit);

        System.out.println("Category limit set successfully!");
    }

    private static void walletMenu() {
        while (true) {
            System.out.println("Wallet Menu:");
            System.out.println("1. View Wallet Info");
            System.out.println("2. Export Wallet Info to TXT");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewWalletInConsole();
                    break;
                case "2":
                    exportWalletToFile();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

   private static void viewWalletInConsole() {
        store.getWallet(currentUser.getWalletId()).showInfoInConsole();
    }

    private static void exportWalletToFile() {
        store.getWallet(currentUser.getWalletId()).showInfoInFile(currentUser.getUsername());
    }
}
