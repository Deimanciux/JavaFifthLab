package Utils;

import dataModels.Overview;
import dataStructures.Category;
import dataStructures.Expense;
import dataStructures.Income;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverviewFactory {
    public static ArrayList<Overview> createOverview(List<Category> categories) {
        ArrayList<Overview> overview = new ArrayList<>();

        for(Category category : categories) {
            for(Income income : category.getIncomes()) {
                overview.add(createIncomeOverview(income));
            }

            for(Expense expense : category.getExpenses()) {
                overview.add(createExpenseOverview(expense));
            }
        }


        return overview;
    }

    private static Overview createIncomeOverview (Income income) {
        Overview overview = new Overview();

        overview.setDescription(income.getDescription());
        overview.setAmount("+" + String.format("%.2f", income.getAmount()));
        overview.setDate(income.getDate());

        return overview;
    }

    private static Overview createExpenseOverview (Expense expense) {
        Overview overview = new Overview();

        overview.setDescription(expense.getDescription());
        overview.setAmount("-" + String.format("%.2f", expense.getAmount()));
        overview.setDate(expense.getDate());

        return overview;
    }

    public static ArrayList<Overview> sortOverview(ArrayList<Overview> overviews) {
        Collections.sort(overviews);

        return overviews;
    }
}