package FXcontrollers.company;

import FXcontrollers.AbstractController;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCompanyPageController extends AbstractController implements Initializable {
    public TextField name;
    public TextField loginName;
    public TextField phoneNumber;
    public TextField email;
    public TextField contactPersonName;
    public TextField contactPersonSurname;
    public Button addCompany;
    public PasswordField password;
    public Button cancel;

    private FinanceManagementSystem fms;
    private User user;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void addCompanyUser() {
        User user;

        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        user = new User(
                name.getText(),
                loginName.getText(),
                password.getText(),
                email.getText(),
                phoneNumber.getText(),
                User.TYPE_COMPANY,
                contactPersonName.getText(),
                contactPersonSurname.getText(),
                fms
        );

        try {
            userRepository.create(user);
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
        } catch (Exception e) {
            ErrorPrinter.printError("This login name is taken. Choose another one.");
            return;
        }

        returnToCompanyMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(loginName.getText()) &&
                isNotEmpty(phoneNumber.getText()) &&
                isNotEmpty(email.getText()) &&
                isNotEmpty(contactPersonName.getText()) &&
                isNotEmpty(contactPersonName.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToCompanyMainPage() {
        new LinksToPages().goToCompanyMainPage(addCompany, fms, user);
    }
}