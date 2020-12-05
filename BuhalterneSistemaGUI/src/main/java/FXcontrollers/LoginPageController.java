package FXcontrollers;

import FXcontrollers.registration.RegistrationPageController;
import HibernateRepository.CategoryRepository;
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
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController extends AbstractController implements Initializable {
    public TextField signInLogInName;
    public TextField signInPassword;
    public Button signIn = new Button();
    public Button register;

    private FinanceManagementSystem fms;
    private User user;
    private final UserRepository userRepository;
    private final FinanceManagementSystemRepository financeManagementSystemRepository;

    public LoginPageController() {
        this.userRepository = new UserRepository(entityManagerFactory);
        this.financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fms = financeManagementSystemRepository.getFmsById(1);
    }

    public void validateUser() throws IOException {
        user = userRepository.getUserByLogin(signInLogInName.getText());

        if (user == null) {
            ErrorPrinter.printError("User with given credentials was not found");
            return;
        }

        if (!user.getPassword().equals(signInPassword.getText())) {
            ErrorPrinter.printError("User with given credentials was not found");
            return;
        }

        loadMainMenuPage();
    }

    private void loadMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(signIn, fms, user);
    }

    public void sendToRegistration() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/registration/RegistrationPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RegistrationPageController registrationPageController = loader.getController();
        registrationPageController.setFms(fms);

        Stage stage = (Stage) signIn.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }
}