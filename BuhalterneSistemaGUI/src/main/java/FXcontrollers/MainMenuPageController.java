package FXcontrollers;

import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuPageController implements Initializable {

    public Button categories;
    public Button individualUsers;
    public Button companyUsers;
    public Button logOut;
    public Button profile;

    private FinanceManagementSystem fms = new FinanceManagementSystem();
    private User user;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;

        if (user.getType().equals(User.TYPE_INDIVIDUAL) || user.getType().equals(User.TYPE_COMPANY)) {
            individualUsers.setVisible(false);
            companyUsers.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void manageCategories() {
        new LinksToPages().goToManageCategoriesPage(categories, fms, user);
    }

    public void manageIndividualUsers() {
        new LinksToPages().goToIndividualPersonMainPage(categories, fms, user);
    }

    public void manageCompanyUsers() {
        new LinksToPages().goToCompanyMainPage(categories, fms, user);
    }

    public void exitProgram() {
        Stage stage = (Stage) categories.getScene().getWindow();
        stage.close();
    }

    public void returnToLoginPage() {
        new LinksToPages().goToLoginPage(logOut, fms );
    }

    public void editProfile() {
        new LinksToPages().goToIndividualPersonUpdatePage(logOut, fms, user, user);
    }
}