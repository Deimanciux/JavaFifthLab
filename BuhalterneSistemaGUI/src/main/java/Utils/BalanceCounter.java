package Utils;

import FXcontrollers.AbstractController;
import dataModels.Balance;
import dataStructures.*;

import java.util.ArrayList;
import java.util.List;

public class BalanceCounter extends AbstractController {
    public static Balance countSystemBalance(FinanceManagementSystem fms) {
        Balance balance = new Balance();

        for (Category category : fms.getCategories()) {
                balance.setIncome(balance.getIncome() + countIncomesSum(category.getIncomes()));
                balance.setExpense(balance.getExpense() + countExpensesSum(category.getExpenses()));
        }

        return balance;
    }

    public static Balance countCategoryBalance(List<Category> categories) {
        Balance balance = new Balance();

        for(Category category : categories) {
            balance.setIncome(balance.getIncome() + countIncomesSum(category.getIncomes()));
            balance.setExpense(balance.getExpense() + countExpensesSum(category.getExpenses()));

            if(category.getSubCategories().size() > 0) {
                countCategoryBalance(category.getSubCategories());
            }
        }

        return balance;
    }

    public static Balance countBalanceByDate(List<Income> paramIncomes, List<Expense> paramExpenses, User user) {
        Balance balance = new Balance();

        List<Income> incomes =  filterUserCategoriesIncomes(paramIncomes, user);
        List<Expense> expenses = filterUserCategoriesExpenses(paramExpenses, user);

        balance.setIncome(balance.getIncome() + countIncomesSum(incomes));
        balance.setExpense(balance.getExpense() + countExpensesSum(expenses));

        return balance;
    }

    private static double countIncomesSum(List<Income> incomes) {
        double incomesAmount = 0;

        for (Income income : incomes) {
            incomesAmount += income.getAmount();
        }

        return incomesAmount;
    }

    private static double countExpensesSum(List<Expense> expenses) {
        double expensesAmount = 0;

        for (Expense expense : expenses) {
            expensesAmount += expense.getAmount();
        }

        return expensesAmount;
    }

    private static List<Income> filterUserCategoriesIncomes(List<Income> incomesToFilter, User user) {
        List<Income> incomes = new ArrayList<>();

        for(Category category : user.getCategories()) {
            for(Income income : incomesToFilter) {
                if(income.getCategory().getId() == category.getId()) {
                    incomes.add(income);
                }
            }
        }

        return incomes;
    }

    private static List<Expense> filterUserCategoriesExpenses(List<Expense> expensesToFilter, User user) {
        List<Expense> expenses = new ArrayList<>();

        for(Category category : user.getCategories()) {
            for(Expense expense : expensesToFilter) {
                if(expense.getCategory().getId() == category.getId()) {
                    expenses.add(expense);
                }
            }
        }

        return expenses;
    }
}