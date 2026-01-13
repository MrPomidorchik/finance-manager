import java.io.*;
import java.util.*;

public class Wallet implements Serializable {
    private UUID walletId;
    private double balance;
    private List<Transaction> transactions;
    private Map<String, Category> categories;

    public Wallet() {
        this.walletId = UUID.randomUUID();
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void addTransaction(Transaction transaction) {
        if (!categories.containsKey(transaction.getCategory())) {
            categories.put(transaction.getCategory(), new Category(transaction.getCategory(), 0.0));
        }
        
        transactions.add(transaction);
        balance += transaction.isIncome() ? transaction.getAmount() : -transaction.getAmount();
    }

    public void setCategoryLimit(String categoryName, double limit) {
        if (categories.containsKey(categoryName)) {
            categories.get(categoryName).setBudget(limit);
        } else {
            System.out.println("Category does not exist.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public void showInfoInConsole() {
        StringBuilder output = new StringBuilder();
        double totalIncome = 0.0;
        double totalExpense = 0.0;
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.isIncome()) {
                totalIncome += t.getAmount();
                incomeByCategory.put(t.getCategory(), incomeByCategory.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            } else {
                totalExpense += t.getAmount();
                expenseByCategory.put(t.getCategory(), expenseByCategory.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        output.append("Wallet UUID: ").append(walletId).append("\n");
        output.append("Balance: ").append(balance).append("\n");
        output.append("Total Income: ").append(totalIncome).append("\n");
        output.append("Income by Category:\n");
        for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
            output.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        output.append("Total Expenses: ").append(totalExpense).append("\n");
        output.append("Expenses by Category:\n");

        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            double expense = entry.getValue();
            double remainingBudget = categories.containsKey(entry.getKey()) ? categories.get(entry.getKey()).getBudget() - expense : 0.0;

            Category category = categories.get(entry.getKey());
            if (category != null && category.getBudget() > 0) {
                if (remainingBudget < 0) {
                    output.append("  ").append(entry.getKey())
                            .append(": ").append(expense)
                            .append(", Remaining Budget: ").append(remainingBudget)
                            .append("/").append(category.getBudget())
                            .append(" Limit exceeded!\n");
                } else {
                    output.append("  ").append(entry.getKey())
                            .append(": ").append(expense)
                            .append(", Remaining Budget: ").append(remainingBudget)
                            .append("/").append(category.getBudget())
                            .append("\n");
                }
            } else {
                output.append("  ").append(entry.getKey())
                        .append(": ").append(expense)
                        .append("\n");
            }
        }

        System.out.println(output.toString());
    }

    public void showInfoInFile(String username) {
        StringBuilder output = new StringBuilder();
        double totalIncome = 0.0;
        double totalExpense = 0.0;
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.isIncome()) {
                totalIncome += t.getAmount();
                incomeByCategory.put(t.getCategory(), incomeByCategory.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            } else {
                totalExpense += t.getAmount();
                expenseByCategory.put(t.getCategory(), expenseByCategory.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        output.append("Wallet UUID: ").append(walletId).append("\n");
        output.append("Balance: ").append(balance).append("\n");
        output.append("Total Income: ").append(totalIncome).append("\n");
        output.append("Income by Category:\n");
        for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
            output.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        output.append("Total Expenses: ").append(totalExpense).append("\n");
        output.append("Expenses by Category:\n");

        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            double expense = entry.getValue();
            double remainingBudget = categories.containsKey(entry.getKey()) ? categories.get(entry.getKey()).getBudget() - expense : 0.0;

            Category category = categories.get(entry.getKey());
            if (category != null && category.getBudget() > 0) {
                if (remainingBudget < 0) {
                    output.append("  ").append(entry.getKey())
                            .append(": ").append(expense)
                            .append(", Remaining Budget: ").append(remainingBudget)
                            .append("/").append(category.getBudget())
                            .append(" Limit exceeded!\n");
                } else {
                    output.append("  ").append(entry.getKey())
                            .append(": ").append(expense)
                            .append(", Remaining Budget: ").append(remainingBudget)
                            .append("/").append(category.getBudget())
                            .append("\n");
                }
            } else {
                output.append("  ").append(entry.getKey())
                        .append(": ").append(expense)
                        .append("\n");
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("wallets_info/" + username + "-" + walletId.toString() + ".txt"))) {
            writer.write(output.toString());
            System.out.println("Wallet info saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving wallet info to file.");
            e.printStackTrace();
        }
    }
}
