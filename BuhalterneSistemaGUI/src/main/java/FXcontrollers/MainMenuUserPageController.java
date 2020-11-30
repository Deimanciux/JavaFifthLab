package FXcontrollers;

import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.scene.control.Button;

public class MainMenuUserPageController {
    public Button categories;
    public Button logOut;
    public Button profile;

    private FinanceManagementSystem fms;
    private User user;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void manageCategories() {
        new LinksToPages().goToManageCategoriesPage(logOut, fms, user);
    }

    public void returnToLoginPage() {
        new LinksToPages().goToLoginPage(logOut);
    }

    public void editProfile() {
        if(user.getType().equals(User.TYPE_INDIVIDUAL)) {
            new LinksToPages().goToIndividualPersonUpdatePage(logOut, fms, user, user);
            return;
        }

        if(user.getType().equals(User.TYPE_COMPANY)) {
            new LinksToPages().goToCompanyUpdatePage(logOut, fms, user, user);
        }
    }
}
