# BudgetMate 
BudgetMate is a console based project.
The BUDGETMATE is a Java-based application designed to help users track their income and expenses efficiently. It allows users to record financial transactions and categorize them for better budget management. The application saves transaction data in a text file to ensure persistence across sessions.
With a simple console-based interface, it provides an easy way to monitor financial health Users can review their income, expenses, and overall balance at any time. 
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Core Java Features Implemented:
This project leverages a variety of essential Java concepts and features to create a robust and user-friendly Personal Finance Manager:

*Object-Oriented Programming (OOP)
The application is designed using OOP principles, defining clear classes such as Transaction and FinanceManager.
Encapsulation is practiced by keeping class fields private and exposing public getter methods, ensuring controlled access to data.
Constructors are used to initialize objects with meaningful state, and method overriding (like toString) provides customized string representations.

*Collections Framework
The dynamic management of transaction records is handled using Java's ArrayList collection.
This allows flexible addition, removal, and iteration over transactions without worrying about fixed array sizes.

*Exception Handling
The program anticipates and handles runtime errors gracefully.
Input parsing (e.g., converting text to numeric values) is wrapped in try-catch blocks to prevent application crashes due to invalid user input.
File input/output operations are also enclosed in exception handling to manage file access errors, such as missing files or permission issues.

*File Input/Output (I/O)
Persistent data storage is achieved by reading from and writing to a text file (transactions.txt) using Java I/O classes like BufferedReader and PrintWriter.
This allows users to save their transactions and retrieve them across different sessions, maintaining continuity.

*String Manipulation
Strings are manipulated extensively for parsing input data and formatting output.
The program uses methods like split() to process lines from the transaction file and StringBuilder for efficiently constructing formatted transaction summaries in HTML for display.

*Data Formatting
The NumberFormat class along with locale settings (Locale("en", "IN")) is used to consistently format monetary values into Indian Rupee currency format.
This enhances readability and provides a professional touch to the financial data presentation.

*Control Flow Structure
Conditional statements (if-else) ensure valid business logic, such as preventing expenses from exceeding income.
Loops (for-each) iterate through transactions to calculate totals, filter results, and display summaries.

*Modular Design and Layered Architecture
The project is organized into separate classes and methods, each responsible for a distinct aspect of the program (data modeling, UI handling, file operations).
This separation improves code maintainability, readability, and facilitates future enhancements or debugging.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Heirchy and Class Order
├── src/
│   ├── FinanceManager.java
│   └── Transaction.java
├── data/
│   └── transactions.txt
├── docs/
│   └── README.md
└── .gitignore
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Features:
~Add income and expense transactions
~Categorize transactions (e.g., Salary, Bills, Entertainment)
~View all transactions in a formatted manner
~Filter transactions by category
~Prevent expense entries from exceeding total income
~Update or delete existing transactions
~Save and load data from a text file
~Friendly error messages and input validation
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Technologies Used:
~Java (JDK 8 or later)
~Java Swing (for optional GUI interface)
~Java I/O (BufferedReader, PrintWriter, FileReader, FileWriter)
~IDE: IntelliJ IDEA / Eclipse / VS Code (any supported IDE)
