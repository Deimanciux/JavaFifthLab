package FXcontrollers.registration;

import FXcontrollers.AbstractController;

import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationPageController extends AbstractController implements Initializable {
    public TextField name;
    public TextField loginName;
    public TextField phoneNumber;
    public TextField email;
    public PasswordField password;
    public Button add;
    public Button cancel;
    public RadioButton isIndividualPerson;
    public RadioButton isCompany;

    private FinanceManagementSystem fms;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);

        ToggleGroup toggleGroup = new ToggleGroup();
        isIndividualPerson.setToggleGroup(toggleGroup);
        isCompany.setToggleGroup(toggleGroup);
        isIndividualPerson.setSelected(true);
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void addUser() throws IOException {
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
                (isCompany.isSelected()) ? User.TYPE_COMPANY : User.TYPE_INDIVIDUAL,
                fms
        );

        try {
            userRepository.create(user);
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
        } catch (Exception e) {
            ErrorPrinter.printError("This login name is taken. Choose another one.");
            return;
        }

        loadMainMenuPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(loginName.getText()) &&
                isNotEmpty(phoneNumber.getText()) &&
                isNotEmpty(email.getText()) &&
                isNotEmpty(password.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void goToLoginPage() {
        new LinksToPages().goToLoginPage(add);
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/LoginPage.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        loader.getController();
//
//        Stage stage = (Stage) add.getScene().getWindow();
//        assert root != null;
//        stage.setScene(new Scene(root));
//        stage.show();
    }

    private void loadMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(add, fms, user);
    }
}