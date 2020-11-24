package FXcontrollers.individualPerson;

import FXcontrollers.AbstractController;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddIndividualPersonPageController extends AbstractController implements Initializable {
    public TextField name;
    public TextField loginName;
    public TextField phoneNumber;
    public TextField email;
    public PasswordField password;
    public Button addIndividualPerson;
    public TextField surname;
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

    public void addIndividualPersonUser() {
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
                User.TYPE_INDIVIDUAL,
                surname.getText(),
                fms
        );

        try {
            userRepository.create(user);
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
        } catch (Exception e) {
            ErrorPrinter.printError("This login name is taken. Choose another one.");
            return;
        }

        returnToIndividualPersonMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(loginName.getText()) &&
                isNotEmpty(phoneNumber.getText()) &&
                isNotEmpty(email.getText()) &&
                isNotEmpty(surname.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToIndividualPersonMainPage() {
        new LinksToPages().goToIndividualPersonMainPage(addIndividualPerson, fms, user);
    }
}