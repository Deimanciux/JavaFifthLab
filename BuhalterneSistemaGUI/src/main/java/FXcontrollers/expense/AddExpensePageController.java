package FXcontrollers.expense;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.ExpenseRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.Expense;
import dataStructures.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddExpensePageController extends AbstractController implements Initializable {
    public TextField id;
    public TextField amount;
    public TextField expenseTaker;
    public Button addExpense;
    public TextArea description;
    public Button cancel;
    public ChoiceBox<String> categoryChoice = new ChoiceBox<>();

    private final ObservableList<String> categoryList = FXCollections.observableArrayList();
    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
        initializeParentCategoryChoiceBox();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        categoryRepository = new CategoryRepository(entityManagerFactory);
        expenseRepository = new ExpenseRepository(entityManagerFactory);
    }

    public void addExpense() {
        Expense expense;

        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!amount.getText().matches(".*\\d.*")){
            ErrorPrinter.printError("Amount has to be numeric");
            return;
        }

        expense = new Expense(
                Double.parseDouble(amount.getText()),
                description.getText(),
                expenseTaker.getText(),
                LocalDateTime.now(),
                category
        );

        expenseRepository.create(expense);
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
        new LinksToPages().goToExpenseMainPage(addExpense, fms, user, category);
    }

    private void initializeParentCategoryChoiceBox() {
        for (Category category : fms.getCategories()) {
            categoryList.add(category.getName());
        }
        categoryList.add("none");
        categoryChoice.setValue("none");
        categoryChoice.setItems(categoryList);
    }
}