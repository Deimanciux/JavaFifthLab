package FXcontrollers.category;

import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteResponsiblePeoplePageController implements Initializable {
    public Button submit;
    public TextField userLogin;
    public Button cancel;

    private FinanceManagementSystem fms;
    private User user;
    private Category category;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void DeleteResponsiblePerson() {
        User user;

        if (category.getResponsiblePerson().size() <= 1) {
            ErrorPrinter.printError("It's only one responsible person. You can't delete it");
            return;
        }

        user = fms.getUserDataByLogin(userLogin.getText());
        if (user != null) {
            category.getResponsiblePerson().remove(user);
        }

        returnToCategoryMainPage();
    }

    public void returnToCategoryMainPage() {
        new LinksToPages().goToManageCategoriesPage(submit, fms, user);
    }
}