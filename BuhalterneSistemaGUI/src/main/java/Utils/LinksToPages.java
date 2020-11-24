package Utils;

import FXcontrollers.category.CategoryMainPageController;
import FXcontrollers.MainMenuPageController;
import FXcontrollers.company.CompanyMainPageController;
import FXcontrollers.expense.ExpenseMainPageController;
import FXcontrollers.income.IncomeMainPageController;
import FXcontrollers.individualPerson.IndividualPersonMainPageController;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LinksToPages {

    public void returnToMainMenuPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/MainMenuPage.fxml"));
        Parent root = loader.load();

        MainMenuPageController mainMenuPageController = loader.getController();
        mainMenuPageController.setFms(fms);
        mainMenuPageController.setUser(user);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToManageCategoriesPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/category/CategoryMainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CategoryMainPageController categoryMainPageController = loader.getController();
        categoryMainPageController.setFms(fms);
        categoryMainPageController.setUser(user);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToCompanyMainPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/company/CompanyMainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CompanyMainPageController companyMainPageController = loader.getController();
        companyMainPageController.setFms(fms);
        companyMainPageController.setUser(user);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToIndividualPersonMainPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/individualPerson/IndividualPersonMainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IndividualPersonMainPageController individualPersonMainPageController = loader.getController();
        individualPersonMainPageController.setFms(fms);
        individualPersonMainPageController.setUser(user);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToIncomeMainPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user, Category category) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/income/IncomeMainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IncomeMainPageController incomeMainPageController = loader.getController();
        incomeMainPageController.setFms(fms);
        incomeMainPageController.setUser(user);
        incomeMainPageController.setCategory(category);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToExpenseMainPage(Button pageStageIdentifier, FinanceManagementSystem fms, User user, Category category) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/expense/ExpenseMainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExpenseMainPageController expenseMainPageController = loader.getController();
        expenseMainPageController.setFms(fms);
        expenseMainPageController.setUser(user);
        expenseMainPageController.setCategory(category);

        Stage stage = (Stage) pageStageIdentifier.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }
}