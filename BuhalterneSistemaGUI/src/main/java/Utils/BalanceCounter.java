package Utils;

import dataModels.Balance;
import dataStructures.Category;
import dataStructures.Expense;
import dataStructures.FinanceManagementSystem;
import dataStructures.Income;

import java.util.List;

public class BalanceCounter {
    public static Balance countSystemBalance(FinanceManagementSystem fms) {
        Balance balance = new Balance();

        for (Category category : fms.getCategories()) {
                balance.setIncome(balance.getIncome() + countCategoryIncomes(category));
                balance.setExpense(balance.getExpense() + countCategoryExpenses(category));
        }

        return balance;
    }

    public static Balance countCategoryBalance(List<Category> categories) {
        Balance balance = new Balance();

        for(Category category : categories) {
            balance.setIncome(balance.getIncome() + countCategoryIncomes(category));
            balance.setExpense(balance.getExpense() + countCategoryExpenses(category));

            if(category.getSubCategories().size() > 0) {
                countCategoryBalance(category.getSubCategories());
            }
        }

        return balance;
    }

    private static double countCategoryIncomes(Category category) {
        double incomesAmount = 0;

        for (Income income : category.getIncomes()) {
            incomesAmount += income.getAmount();
        }

        return incomesAmount;
    }

    private static double countCategoryExpenses(Category category) {
        double expensesAmount = 0;

        for (Expense expense : category.getExpenses()) {
            expensesAmount += expense.getAmount();
        }

        return expensesAmount;
    }
}