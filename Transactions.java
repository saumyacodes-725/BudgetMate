public class Transaction {
    //private cause of encapsulation
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
//getter methods
    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return type + ": " + description + " - " + amount + " (" + category + ")";
    }

    public String toFileString() {
        return description + ";" + amount + ";" + type + ";" + category;
    }
}

