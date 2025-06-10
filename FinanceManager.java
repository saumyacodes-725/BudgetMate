import java.io.*;
import java.util.*;

public class FinanceManager {
    private static final String FILE_NAME = "transactions.txt";
    private List<Transaction> transactions = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new FinanceManager().run();
    }

    public void run() {
        loadTransactions();

        while (true) {
            System.out.println("\n--- Finance Manager Menu ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Transactions");
            System.out.println("3. Delete Transaction");
            System.out.println("4. Update Transaction");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addTransaction(); break;
                case "2": viewTransactions(); break;
                case "3": deleteTransaction(); break;
                case "4": updateTransaction(); break;
                case "5": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void addTransaction() {
        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if(amount < 0) {
                System.out.println("Amount cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.print("Type (Income/Expense): ");
        String type = scanner.nextLine();
        if (!type.equalsIgnoreCase("Income") && !type.equalsIgnoreCase("Expense")) {
            System.out.println("Invalid type.");
            return;
        }

        System.out.print("Category: ");
        String category = scanner.nextLine();

        Transaction t = new Transaction(desc, amount, capitalize(type), category);

        // Check total income/expense logic
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction tr : transactions) {
            if (tr.getType().equalsIgnoreCase("Income")) totalIncome += tr.getAmount();
            else totalExpense += tr.getAmount();
        }

        if (t.getType().equalsIgnoreCase("Income")) totalIncome += t.getAmount();
        else totalExpense += t.getAmount();

        if (totalExpense > totalIncome) {
            System.out.println("Error: Total expenses cannot exceed total income!");
            return;
        }

        transactions.add(t);
        saveTransactions();
        System.out.println("Transaction added.");
    }

    private void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nTransactions:");
        int idx = 1;
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction t : transactions) {
            System.out.println(idx++ + ". " + t);
            if (t.getType().equalsIgnoreCase("Income")) totalIncome += t.getAmount();
            else totalExpense += t.getAmount();
        }

        System.out.printf("Total Income: %.2f\n", totalIncome);
        System.out.printf("Total Expense: %.2f\n", totalExpense);
        System.out.printf("Net Balance: %.2f\n", totalIncome - totalExpense);
    }

    private void deleteTransaction() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to delete.");
            return;
        }

        viewTransactions();

        System.out.print("Enter transaction number to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index < 1 || index > transactions.size()) {
                System.out.println("Invalid transaction number.");
                return;
            }
            Transaction removed = transactions.remove(index - 1);
            saveTransactions();
            System.out.println("Deleted: " + removed);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void updateTransaction() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available to update.");
            return;
        }

        viewTransactions();

        System.out.print("Enter transaction number to update: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine());
            if (index < 1 || index > transactions.size()) {
                System.out.println("Invalid transaction number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        Transaction oldT = transactions.get(index - 1);

        System.out.println("Leave blank to keep current value.");

        System.out.print("New description (current: " + oldT.getDescription() + "): ");
        String newDesc = scanner.nextLine();
        if (newDesc.isEmpty()) newDesc = oldT.getDescription();

        System.out.print("New amount (current: " + oldT.getAmount() + "): ");
        String amountStr = scanner.nextLine();
        double newAmount;
        if (amountStr.isEmpty()) {
            newAmount = oldT.getAmount();
        } else {
            try {
                newAmount = Double.parseDouble(amountStr);
                if (newAmount < 0) {
                    System.out.println("Amount cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
                return;
            }
        }

        System.out.print("New type (Income/Expense) (current: " + oldT.getType() + "): ");
        String newType = scanner.nextLine();
        if (newType.isEmpty()) newType = oldT.getType();
        else if (!newType.equalsIgnoreCase("Income") && !newType.equalsIgnoreCase("Expense")) {
            System.out.println("Invalid type.");
            return;
        }
        newType = capitalize(newType);

        System.out.print("New category (current: " + oldT.getCategory() + "): ");
        String newCategory = scanner.nextLine();
        if (newCategory.isEmpty()) newCategory = oldT.getCategory();

        // Check totals after update
        double totalIncome = 0;
        double totalExpense = 0;
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = (i == index - 1) ? new Transaction(newDesc, newAmount, newType, newCategory) : transactions.get(i);
            if (t.getType().equalsIgnoreCase("Income")) totalIncome += t.getAmount();
            else totalExpense += t.getAmount();
        }

        if (totalExpense > totalIncome) {
            System.out.println("Error: Total expenses cannot exceed total income!");
            return;
        }

        transactions.set(index - 1, new Transaction(newDesc, newAmount, newType, newCategory));
        saveTransactions();
        System.out.println("Transaction updated.");
    }

    private void loadTransactions() {
        transactions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 4);
                if (parts.length == 4) {
                    String desc = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String type = parts[2];
                    String category = parts[3];
                    transactions.add(new Transaction(desc, amount, type, category));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous transactions found or error reading file.");
        }
    }

    private void saveTransactions() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Transaction t : transactions) {
                pw.println(t.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions.");
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

class Transaction {
    private String description;
    private double amount;
    private String type;
    private String category;

    public Transaction(String description, double amount, String type, String category) {
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getCategory() { return category; }

    public String toFileString() {
        return description + ";" + amount + ";" + type + ";" + category;
    }

    @Override
    public String toString() {
        return type + ": " + description + " - " + amount + " (" + category + ")";
    }
}
