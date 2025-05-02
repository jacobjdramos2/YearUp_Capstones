# 💰 FinanceTracker - Java CLI Accounting Ledger

FinanceTracker is a command-line Java application that allows users to track financial transactions — including deposits, payments, and customized financial reports. 

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
- **6)** Custom Search 

---
## PHOTO 
<img width="656" alt="Screenshot 2025-05-02 at 10 07 06 AM" src="https://github.com/user-attachments/assets/39f9a55d-ba42-4c7b-9641-e0ef1de7970c" />


## 🧠 Interesting Code 

```java
    public ArrayList<Transaction> customSearch(String startDateInput, String endDateInput,
                                               String vendorInput, String descriptionInput,
                                               String amountInput) {
        ArrayList<Transaction> result = new ArrayList<>();

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double amount = null;

        // Convert string inputs to usable types
        if (!startDateInput.isEmpty()) {
            try {
                startDate = LocalDate.parse(startDateInput);
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }

        if (!endDateInput.isEmpty()) {
            try {
                endDate = LocalDate.parse(endDateInput);
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }

        if (!amountInput.isEmpty()) {
            try {
                amount = Double.parseDouble(amountInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Skipping amount filter.");
            }
        }

        for (Transaction t : transactions) {
            LocalDate tDate = t.getLocalDate();

            if (startDate != null && tDate.isBefore(startDate)) continue;
            if (endDate != null && tDate.isAfter(endDate)) continue;
            if (!vendorInput.isEmpty() && !t.getVendor().toLowerCase().contains(vendorInput.toLowerCase())) continue;
            if (!descriptionInput.isEmpty() && !t.getDescription().toLowerCase().contains(descriptionInput.toLowerCase())) continue;
            if (amount != null && t.getAmount() != amount) continue;

            result.add(t);
        }

        return result;
    }
