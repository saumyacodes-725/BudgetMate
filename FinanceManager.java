import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class FinanceManager extends JFrame {
    private static final String FILE_NAME = "transactions.txt";
    private final List<Transaction> transactions;

    private JTextField descriptionField, amountField;
    private JComboBox<String> typeComboBox, categoryComboBox;
    private JEditorPane displayArea;
    private JComboBox<String> filterCategoryBox;

    public FinanceManager() {
        setTitle("Personal Finance Manager");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        transactions = new ArrayList<>();
        loadTransactions();

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 5));

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Income", "Expense"});
        typeComboBox.setSelectedIndex(0);
        inputPanel.add(typeComboBox);

        inputPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(new String[]{
                "Salary", "Business", "Other income", "Trade", 
                "Bills", "Groceries", "Entertainments", "Utilities", "Other expenses"
        });
        categoryComboBox.setSelectedIndex(0);
        inputPanel.add(categoryComboBox);

        JButton addButton = new JButton("Add Transaction");
        inputPanel.add(addButton);
        addButton.addActionListener(e -> addTransaction());

        JButton viewButton = new JButton("View All");
        inputPanel.add(viewButton);
        viewButton.addActionListener(e -> viewTransactions());

        // Display Area
        displayArea = new JEditorPane();
        displayArea.setContentType("text/html");
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterCategoryBox = new JComboBox<>(new String[]{
                "All", "Salary", "Business", "Other income", "Trade",
                "Bills", "Groceries", "Entertainments", "Utilities", "Other expenses"
        });
        JButton filterButton = new JButton("Filter");

        filterButton.addActionListener(e -> {
            String selectedCategory = (String) filterCategoryBox.getSelectedItem();
            filterTransactions(selectedCategory);
        });

        filterPanel.add(new JLabel("Filter Category:"));
        filterPanel.add(filterCategoryBox);
        filterPanel.add(filterButton);

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.SOUTH);
    }

    // Transaction Class
    class Transaction {
        private final String description;
        private final double amount;
        private final String type;
        private final String category;

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
    }

    private void addTransaction() {
        String description = descriptionField.getText().trim();
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = (String) typeComboBox.getSelectedItem();
        String category = (String) categoryComboBox.getSelectedItem();

        Transaction transaction = new Transaction(description, amount, type, category);
        transactions.add(transaction);

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            if (t.getType().equals("Income")) totalIncome += t.getAmount();
            else totalExpense += t.getAmount();
        }

        if (totalExpense > totalIncome) {
            transactions.remove(transaction);
            JOptionPane.showMessageDialog(this, "Error: Total expenses cannot exceed total income!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            saveTransactions();
            clearFields();
        }
    }

    private void clearFields() {
        descriptionField.setText("");
        amountField.setText("");
        typeComboBox.setSelectedIndex(0);
        categoryComboBox.setSelectedIndex(0);
    }

    private void viewTransactions() {
        NumberFormat inrFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        StringBuilder sb = new StringBuilder("<html><body style='font-family:sans-serif;'>");
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            String amountStr = inrFormat.format(transaction.getAmount());
            sb.append(transaction.getDescription())
                    .append(" - ").append(amountStr)
                    .append(" - ").append(transaction.getType())
                    .append(" - ").append(transaction.getCategory())
                    .append("<br>");

            if (transaction.getType().equals("Income")) totalIncome += transaction.getAmount();
            else totalExpense += transaction.getAmount();
        }

        sb.append("<br>");
        sb.append("<span style='color:#008080; font-weight:bold;'>Total Income: ")
                .append(inrFormat.format(totalIncome)).append("</span><br>");
        sb.append("<span style='color:#FFA07A; font-weight:bold;'>Total Expense: ")
                .append(inrFormat.format(totalExpense)).append("</span><br>");
        sb.append("<span style='font-weight:bold;'>Net Balance: ")
                .append(inrFormat.format(totalIncome - totalExpense)).append("</span><br>");
        sb.append("</body></html>");

        displayArea.setText(sb.toString());
    }

    private void filterTransactions(String categoryFilter) {
        NumberFormat inrFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        StringBuilder sb = new StringBuilder("<html><body style='font-family:sans-serif;'>");
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            if (!categoryFilter.equals("All") && !transaction.getCategory().equals(categoryFilter)) {
                continue;
            }

            String amountStr = inrFormat.format(transaction.getAmount());
            sb.append(transaction.getDescription())
                    .append(" - ").append(amountStr)
                    .append(" - ").append(transaction.getType())
                    .append(" - ").append(transaction.getCategory())
                    .append("<br>");

            if (transaction.getType().equals("Income")) totalIncome += transaction.getAmount();
            else totalExpense += transaction.getAmount();
        }

        sb.append("<br>");
        sb.append("<span style='color:#008080; font-weight:bold;'>Total Income: ")
                .append(inrFormat.format(totalIncome)).append("</span><br>");
        sb.append("<span style='color:#FFA07A; font-weight:bold;'>Total Expense: ")
                .append(inrFormat.format(totalExpense)).append("</span><br>");
        sb.append("<span style='font-weight:bold;'>Net Balance: ")
                .append(inrFormat.format(totalIncome - totalExpense)).append("</span><br>");
        sb.append("</body></html>");

        displayArea.setText(sb.toString());
    }

    private void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 4);
                if (parts.length == 4) {
                    String description = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String type = parts[2].trim();
                    String category = parts[3].trim();
                    transactions.add(new Transaction(description, amount, type, category));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous transactions found.");
        }
    }

    private void saveTransactions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Transaction transaction : transactions) {
                writer.println(transaction.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Failed to save transactions.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FinanceManager().setVisible(true));
    }
}
