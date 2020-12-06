package dataModels;

public class Balance {
    private double income;
    private double expense;

    public Balance() {
        this.income = 0;
        this.expense = 0;
    }

    public double getBalance() {
        return income - expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}