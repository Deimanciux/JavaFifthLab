package FXcontrollers;

import FXcontrollers.company.AddCompanyPageController;
import FXcontrollers.individualPerson.AddIndividualPersonPageController;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginPageController extends AbstractController implements Initializable {
    public TextField signInPassword;
    public Button signIn = new Button();
    public Button register;
    public TextField signInLogInName;

    private FinanceManagementSystem fms = null;
    private boolean isIndividualPerson = false;
    private boolean isCompany = false;
    private User user;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public LoginPageController() {
        this.userRepository = new UserRepository(entityManagerFactory);
        this.financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fms = financeManagementSystemRepository.getFmsById(1);

        getUserType();
    }

    private void getUserType() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("User Types");
        alert.setHeaderText("Choose User You Want To Work With");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Individual");
        ButtonType buttonTypeTwo = new ButtonType("Company");
        ButtonType buttonTypeThree = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            isIndividualPerson = true;
        } else if (result.get() == buttonTypeTwo) {
            isCompany = true;
        } else {
            Platform.exit();
        }
    }

    public void validateUser() throws IOException {
        this.user = this.userRepository.getUserById(7);
        loadMainMenuPage();

//       if(isUserWithEnteredDataExists() ) {
//            loadMainMenuPage();
//        } else {
//            noSuchUser();
//        }
    }

    private boolean isUserWithEnteredDataExists() {
        return fms.getUsers().stream().anyMatch(
                user -> user.getLoginName().equals(signInLogInName.getText()) &&
                        user.getPassword().equals(signInPassword.getText()));
    }

    private void noSuchUser() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Finance Management System");
        alert.setHeaderText("No such user");
        alert.setContentText("User with given credentials was not found. Contact the administrator.");
        alert.showAndWait();
    }

    public void sendToRegistration() {
//        if (isIndividualPerson) {
//            loadIndividualPersonRegistration();
//        } else {
//            loadCompanyRegistration();
//        }
    }

    private void loadMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(signIn, fms, user);
    }

//    private void loadIndividualPersonRegistration() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/individualPerson/AddIndividualPersonPage.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        AddIndividualPersonPageController addIndividualPersonPageController = loader.getController();
//        addIndividualPersonPageController.setFms(fms);
//
//        Stage stage = (Stage) signIn.getScene().getWindow();
//        assert root != null;
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//    private void loadCompanyRegistration() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/company/AddCompanyPage.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        AddCompanyPageController addCompanyPageController = loader.getController();
//        addCompanyPageController.setFms(fms);
//
//        Stage stage = (Stage) signIn.getScene().getWindow();
//        assert root != null;
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
}