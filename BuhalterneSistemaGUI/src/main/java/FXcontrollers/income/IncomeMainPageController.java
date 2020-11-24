package FXcontrollers.income;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.IncomeRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.Income;
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

public class IncomeMainPageController extends AbstractController implements Initializable {
    public MenuItem create;
    public MenuItem update;
    public ListView<String> incomeList;
    public Button viewDetails;
    public Button delete;
    public ListView<String> showData;
    public MenuItem goToCategories;

    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private IncomeRepository incomeRepository;
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
        showAllIncome();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incomeRepository = new IncomeRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        categoryRepository = new CategoryRepository(entityManagerFactory);
    }

    public void viewIncomeDetails() {
        Income income;
        showData.getItems().clear();

        income = getIncomeFromSelection();

        if (income != null) {
            showData.getItems().add(income.toString());
        }
    }

    public void deleteIncome() {
        Income income;
        income = getIncomeFromSelection();

        if (income == null) {
            return;
        }

        incomeRepository.remove(income.getId());
        this.fms = financeManagementSystemRepository.getFmsById(fms.getId());

        new LinksToPages().goToIncomeMainPage(viewDetails, fms, user, category);
    }

    public void updateIncome() {
        if (getIncomeFromSelection() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/Income/UpdateIncomePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateIncomePageController updateIncomePageController = loader.getController();
        updateIncomePageController.setFms(fms);
        updateIncomePageController.setUser(user);
        updateIncomePageController.setIncome(getIncomeFromSelection());
        updateIncomePageController.setCategory(category);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createIncome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/Income/AddIncomePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddIncomePageController addIncomePageController = loader.getController();
        addIncomePageController.setFms(fms);
        addIncomePageController.setUser(user);
        addIncomePageController.setCategory(category);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAllIncome() {
        incomeList.getItems().clear();

        if (category == null) {
            return;
        }

        category = categoryRepository.getCategoryById(category.getId());
        category.getIncomes().forEach(income -> incomeList.getItems().add(income.getId() + " " + income.getDescription()));
    }

    private Income getIncomeFromSelection() {
        Income income;

        if (incomeList.getSelectionModel().getSelectedItem() == null) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        String[] values = incomeList.getSelectionModel().getSelectedItem().split(" ");
        income = incomeRepository.getIncomeById(Integer.parseInt(values[0]));

        return income;
    }

    public void returnToCategories() {
        new LinksToPages().goToManageCategoriesPage(viewDetails, fms, user);
    }
}