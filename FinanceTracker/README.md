# 💰 FinanceTracker - Java CLI Accounting Ledger

FinanceTracker is a command-line Java application that allows users to track financial transactions — including deposits, payments, and customized financial reports. It's designed for personal or small business use and stores all data in a `transactions.csv` file for persistence.

---

## 🚀 Features

### 🏠 Home Screen
- **D)** Add Deposit
- **P)** Make Payment (debit)
- **L)** Open Ledger
- **X)** Exit the application

### 📓 Ledger Screen
- **A)** View all transactions
- **D)** View deposits only
- **P)** View payments only
- **R)** Run reports (Month-to-date, Vendor search, etc.)
- **H)** Return to home screen

### 📊 Reports
- **1)** Month To Date
- **2)** Previous Month
- **3)** Year To Date
- **4)** Previous Year
- **5)** Search by Vendor
- **0)** Back to Ledger
- *(Optional)* **6)** Custom Search (if implemented)

---

## 🧠 Interesting Code Example

This method filters transactions for the current month using `LocalDate`:

```java
public ArrayList<Transaction> getMonthToDate() {
    ArrayList<Transaction> result = new ArrayList<>();
    LocalDate now = LocalDate.now();
    for (Transaction t : transactions) {
        LocalDate tDate = t.getLocalDate();
        if (tDate.getYear() == now.getYear() && tDate.getMonth() == now.getMonth()) {
            result.add(t);
        }
    }
    return result;
}
