package FXcontrollers.expense;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.ExpenseRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import Utils.Constants;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.Expense;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateExpensePageController extends AbstractController implements Initializable {
    public TextField amount;
    public Button updateExpense;
    public TextField expenseTaker;
    public TextArea description;
    public Button cancel;
    public TextField date;

    private FinanceManagementSystem fms;
    private User user;
    private Expense expense;
    private Category category;
    private ExpenseRepository expenseRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private CategoryRepository categoryRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
        amount.setText(String.valueOf(expense.getAmount()));
        expenseTaker.setText(expense.getExpenseTaker());
        description.setText(expense.getDescription());
        date.setText(expense.getDate().format(Constants.DATE_FORMAT));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expenseRepository = new ExpenseRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        categoryRepository = new CategoryRepository(entityManagerFactory);
    }

    public void updateExpense() {
        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!amount.getText().matches(".*\\d.*")){
            ErrorPrinter.printError("Amount has to be numeric");
            return;
        }

        expense.setDate(LocalDateTime.parse(date.getText(), Constants.DATE_FORMAT));
        expense.setAmount(Double.parseDouble(amount.getText()));
        expense.setDescription(description.getText());
        expense.setExpenseTaker(expenseTaker.getText());

        expenseRepository.edit(expense);
        category = categoryRepository.getCategoryById(category.getId());
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        returnToExpenseMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(amount.getText()) &&
                isNotEmpty(description.getText()) &&
                isNotEmpty(expenseTaker.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToExpenseMainPage() {
        new LinksToPages().goToExpenseMainPage(updateExpense, fms, user, category);
    }
}