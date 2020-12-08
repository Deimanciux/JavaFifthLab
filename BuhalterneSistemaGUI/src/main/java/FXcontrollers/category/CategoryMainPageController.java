package FXcontrollers.category;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.IncomeRepository;
import Utils.BalanceCounter;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataModels.Balance;
import dataStructures.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryMainPageController extends AbstractController implements Initializable {
    public Button viewDetails;
    public TreeView<Category> treeView;
    public MenuItem create;
    public MenuItem update;
    public ListView<String> showData;
    public Button delete;
    public MenuItem goToMenu;
    public MenuItem addUsers;
    public MenuItem deleteUsers;
    public MenuItem manageIncomeBtn;
    public MenuItem manageExpensesBtn;
    public Button viewDetailsSystem;
    public Button viewDetailsCategory;
    public Label systemBalanceLabel;
    public Label categoryBalanceLabel;
    public Label balanceByDate;
    public DatePicker fromDate;
    public DatePicker toDate;
    public Button generate;

    private FinanceManagementSystem fms;
    private User user;
    private CategoryRepository categoryRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private IncomeRepository incomeRepository;
    private Balance systemBalance;
    private Balance categoryBalance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryRepository = new CategoryRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        incomeRepository = new IncomeRepository(entityManagerFactory);
        categoryBalanceLabel.setText("0.00");

    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = financeManagementSystemRepository.getFmsById(fms.getId());
    }

    public void setUser(User user) {
        this.user = user;

        if (user.getType().equals(User.TYPE_COMPANY) || user.getType().equals(User.TYPE_INDIVIDUAL)) {
            fillTreeViewWithUserCategories(user.getCategories());
        } else {
            fillCategoryTreeView();
        }

        getSystemBalance();
    }

    private void getSystemBalance() {
        systemBalance = BalanceCounter.countSystemBalance(fms);
        systemBalanceLabel.setText(String.valueOf(String.format("%.2f", systemBalance.getBalance())));
    }

    private void fillTreeViewWithUserCategories(List<Category> categories) {
        treeView.setRoot(new TreeItem<>());
        treeView.getRoot().setExpanded(true);

        for (Category category : categories) {
            TreeItem<Category> categoryTreeItem = new TreeItem<>(category);
            treeView.getRoot().getChildren().add(categoryTreeItem);
        }

        treeView.setShowRoot(false);
    }

    private void fillCategoryTreeView() {
        treeView.setRoot(new TreeItem<>());
        treeView.getRoot().setExpanded(true);

        for (Category category : fms.getCategories()) {

            if (category.getParentCategory() == null) {
                addTreeItems(category, treeView.getRoot());
            }
        }

        treeView.setShowRoot(false);
    }

    private void addTreeItems(Category category, TreeItem<Category> parentItem) {
        TreeItem<Category> categoryTreeItem = new TreeItem<>(category);
        parentItem.getChildren().add(categoryTreeItem);
        category.getSubCategories().forEach(subCategory -> addTreeItems(subCategory, categoryTreeItem));
    }

    public void viewCategoryDetails() {
        showData.getItems().clear();

        if (getLeadSelect() == null) {
            return;
        }

        showData.getItems().add(getLeadSelect().printCategoryData());
    }

    private Category getLeadSelect() {

        if (isNotItemSelectedOnTreeView()) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        TreeItem<Category> selectedItem = treeView.getSelectionModel().getSelectedItem();
        return selectedItem.getValue();
    }

    public void createCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/category/AddCategoryPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddCategoryPageController addCategoryPageController = loader.getController();
        addCategoryPageController.setFms(fms);
        addCategoryPageController.setUser(user);

        Stage stage = (Stage) viewDetails.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateCategory() {
        if (getLeadSelect() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/category/UpdateCategoryPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateCategoryPageController updateCategoryPageController = loader.getController();
        updateCategoryPageController.setFms(fms);
        updateCategoryPageController.setUser(user);
        updateCategoryPageController.setCategory(getLeadSelect());

        Stage stage = (Stage) viewDetails.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCategory() {
        Category category;

        if (getLeadSelect() == null) {
            return;
        }

        category = getLeadSelect();

        List<User> respPeople = new ArrayList<>(category.getResponsiblePerson());

        for (User user : respPeople) {
            user.removeCategory(category);
        }

        if (category.getSubCategories().size() > 0) {
            removeUsersFromCategories(category.getSubCategories());
        }

        categoryRepository.remove(category.getId());
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        returnToCategoryMainPage();
    }

    public void removeUsersFromCategories(List<Category> categories) {
        for (Category category : categories) {
            List<User> respPeople = new ArrayList<>(category.getResponsiblePerson());

            for (User user : respPeople) {
                user.removeCategory(category);
            }

            if (category.getSubCategories().size() > 0) {
                removeUsersFromCategories(category.getSubCategories());
            }
        }
    }

    public void returnToMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(viewDetails, fms, user);
    }

    public void returnToCategoryMainPage() {
        new LinksToPages().goToManageCategoriesPage(viewDetails, fms, user);
    }

    public void addResponsiblePeople() {
        if (getLeadSelect() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/category/AddResponsiblePeoplePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddResponsiblePeoplePageController addResponsiblePeoplePageController = loader.getController();
        addResponsiblePeoplePageController.setFms(fms);
        addResponsiblePeoplePageController.setUser(user);
        addResponsiblePeoplePageController.setCategory(getLeadSelect());

        Stage stage = (Stage) viewDetails.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteResponsiblePeople() {
        if (getLeadSelect() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/category/DeleteResponsiblePeoplePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeleteResponsiblePeoplePageController deleteResponsiblePeoplePageController = loader.getController();
        deleteResponsiblePeoplePageController.setFms(fms);
        deleteResponsiblePeoplePageController.setUser(user);
        deleteResponsiblePeoplePageController.setCategory(getLeadSelect());

        Stage stage = (Stage) viewDetails.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void manageIncomes() {
        if (getLeadSelect() == null) {
            return;
        }

        new LinksToPages().goToIncomeMainPage(viewDetails, fms, user, getLeadSelect());
    }

    public void manageExpenses() {
        if (getLeadSelect() == null) {
            return;
        }

        new LinksToPages().goToExpenseMainPage(viewDetails, fms, user, getLeadSelect());
    }

    public void showBalanceDialog(double balance, double income, double expense) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System balance dialog");
        alert.setHeaderText(null);
        alert.setContentText("Balance: " +
                String.format("%.2f", balance) +
                "\n" + "Income amount: " +
                String.format("%.2f", income) +
                "\n" + "Expense amount: " +
                String.format("%.2f", expense)
        );

        alert.showAndWait();
    }

    public void showSystemBalanceDetails() {
        showBalanceDialog(systemBalance.getBalance(), systemBalance.getIncome(), systemBalance.getExpense());
    }

    public void showCategoryBalanceDetails() {
        showBalanceDialog(categoryBalance.getBalance(), categoryBalance.getIncome(), categoryBalance.getExpense());
    }

    private boolean isNotItemSelectedOnTreeView() {
        return treeView.getSelectionModel().getSelectedItem() == null;
    }

    public void showSelected() {
        Category category;
        List<Category> categories = new ArrayList<>();

        if (isNotItemSelectedOnTreeView()) {
            return;
        }

        category = getLeadSelect();
        categories.add(category);
        categoryBalance = BalanceCounter.countCategoryBalance(categories);
        categoryBalanceLabel.setText(String.valueOf(String.format("%.2f", categoryBalance.getBalance())));
    }

    public void generateBalanceByDate() {
        List<Income> incomes;
        incomes = incomeRepository.getIncomesByDate(fromDate.getValue(), toDate.getValue());
        System.out.println(incomes);
    }
}