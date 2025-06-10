BudgetMate
BudgetMate is a console-based Java application designed to help users efficiently track their income and expenses. It allows users to record financial transactions and categorize them for better budget management. The application persists transaction data in a text file, enabling continuity across sessions.
With its simple console interface, users can easily monitor their financial health by reviewing income, expenses, and overall balance at any time.


Core Java Features Implemented:
This project leverages a variety of essential Java concepts and features to create a robust and user-friendly Personal Finance Manager:

Object-Oriented Programming (OOP):
Designed using OOP principles with clear classes like Transaction and FinanceManager. Encapsulation is enforced with private fields and public getters. Constructors initialize object state, and method overriding (e.g., toString()) provides customized string representations.

Collections Framework:
Uses ArrayList to dynamically manage transaction records, allowing flexible addition, removal, and iteration without fixed array sizes.

Exception Handling:
Anticipates runtime errors gracefully. Input parsing (such as converting text to numbers) uses try-catch blocks to avoid crashes due to invalid input. File I/O operations are also wrapped with exception handling to manage errors like missing files or permission issues.

File Input/Output (I/O):
Transaction data is persistently stored in transactions.txt using Java I/O classes like BufferedReader and PrintWriter. This enables data saving and retrieval across sessions.

String Manipulation:
Extensively uses string methods like split() for parsing file data and StringBuilder for efficiently constructing formatted output summaries.

Data Formatting:
Utilizes NumberFormat with the locale set to Indian Rupees (Locale("en", "IN")) to format monetary values consistently and professionally.

Control Flow Structures:
Implements conditionals to enforce business rules, such as preventing expenses from exceeding income. Uses loops to process and summarize transactions.

Modular Design and Layered Architecture:
Separates concerns across classes and methods handling data modeling, UI, and file operations. This modularity improves maintainability and ease of future enhancements.



Project Hierarchy and Class Organization:
BudgetMate/
├── src/
│   ├── FinanceManager.java
│   └── Transaction.java
├── data/
│   └── transactions.txt
├── docs/
│   └── README.md
└── .gitignore



Features:
Add income and expense transactions

Categorize transactions (e.g., Salary, Bills, Entertainment)

View all transactions in a formatted display

Filter transactions by category

Prevent expense entries from exceeding total income

Update or delete existing transactions

Save and load data from a text file for persistence

Friendly error messages and input validation



Technologies Used:
Java (JDK 8 or later)

Java Swing (optional GUI interface)

Java I/O (BufferedReader, PrintWriter, FileReader, FileWriter)

IDEs: IntelliJ IDEA, Eclipse, VS Code, or any Java-supporting IDE
