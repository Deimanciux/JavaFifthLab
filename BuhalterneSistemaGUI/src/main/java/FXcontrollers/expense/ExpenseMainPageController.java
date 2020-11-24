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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExpenseMainPageController extends AbstractController implements Initializable {
    public MenuItem create;
    public MenuItem update;
    public ListView<String> expenseList;
    public Button viewDetails;
    public Button delete;
    public ListView<String> showData;
    public MenuItem goToCategories;

    private FinanceManagementSystem fms;
    private User user;
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
        showAllExpense();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        categoryRepository = new CategoryRepository(entityManagerFactory);
        expenseRepository = new ExpenseRepository(entityManagerFactory);
    }

    public void createExpense() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/expense/AddExpensePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddExpensePageController addExpensePageController = loader.getController();
        addExpensePageController.setFms(fms);
        addExpensePageController.setUser(user);
        addExpensePageController.setCategory(category);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateExpense() {
        if (getExpenseFromSelection() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/expense/UpdateExpensePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateExpensePageController updateExpensePageController = loader.getController();
        updateExpensePageController.setFms(fms);
        updateExpensePageController.setUser(user);
        updateExpensePageController.setExpense(getExpenseFromSelection());
        updateExpensePageController.setCategory(category);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void viewExpenseDetails() {
        Expense Expense;

        showData.getItems().clear();
        Expense = getExpenseFromSelection();
        if (Expense != null) {
            showData.getItems().add(Expense.toString());
        }
    }

    public void deleteExpense() {
        Expense expense;

        expense = getExpenseFromSelection();

        if (expense == null) {
            return;
        }

        expenseRepository.remove(expense.getId());
        category = categoryRepository.getCategoryById(category.getId());
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        new LinksToPages().goToExpenseMainPage(viewDetails, fms, user, category);
    }

    private void showAllExpense() {
        expenseList.getItems().clear();

        category = categoryRepository.getCategoryById(category.getId());
        category.getExpenses().forEach(expense -> expenseList.getItems().add(expense.getId() + " " + expense.getDescription()));
    }

    private Expense getExpenseFromSelection() {
        Expense expense;

        if (expenseList.getSelectionModel().getSelectedItem() == null) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        String[] values = expenseList.getSelectionModel().getSelectedItem().split(" ");
        expense = category.getExpenseData(Integer.parseInt(values[0]));

        return expense;
    }

    public void returnToCategoriesMainPage() {
        new LinksToPages().goToManageCategoriesPage(viewDetails, fms, user);
    }
}