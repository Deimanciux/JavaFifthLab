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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateIndividualPersonPageController extends AbstractController implements Initializable {
    public TextField name;
    public TextField phoneNumber;
    public TextField email;
    public Button update;
    public PasswordField password;
    public TextField surname;
    public Button cancel;
    public TextField loginName;

    private FinanceManagementSystem fms;
    private User user;
    private User individualPerson;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIndividualPerson(User individualPerson) {
        this.individualPerson = individualPerson;
        name.setText(individualPerson.getName());
        phoneNumber.setText(individualPerson.getPhoneNumber());
        email.setText(individualPerson.getEmail());
        surname.setText(individualPerson.getSurname());
        loginName.setText(individualPerson.getLoginName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void updateIndividualPerson() throws IOException {
        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!password.getText().equals("")) {
            individualPerson.setPassword(password.getText());
        }

        individualPerson.setName(name.getText());
        individualPerson.setLoginName(loginName.getText());
        individualPerson.setEmail(email.getText());
        individualPerson.setPhoneNumber(phoneNumber.getText());
        individualPerson.setSurname(surname.getText());

        try {
            userRepository.edit(individualPerson);
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
        } catch (Exception e) {
            ErrorPrinter.printError("This login name is taken. Choose another one.");
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
            return;
        }

        returnToPreviousPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(loginName.getText()) &&
                isNotEmpty(email.getText()) &&
                isNotEmpty(phoneNumber.getText()) &&
                isNotEmpty(surname.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToPreviousPage() throws IOException {
        if (user.getType().equals(User.TYPE_ADMIN)) {
            new LinksToPages().goToIndividualPersonMainPage(update, fms, user);
        } else {
            new LinksToPages().returnToMainMenuPage(cancel, fms, user);
        }
    }
}