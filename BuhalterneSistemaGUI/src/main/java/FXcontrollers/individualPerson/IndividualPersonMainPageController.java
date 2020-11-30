package FXcontrollers.individualPerson;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndividualPersonMainPageController extends AbstractController implements Initializable {
    public MenuItem create;
    public MenuItem update;
    public MenuItem goToMenu;
    public ListView<String> IndividualPersonList;
    public Button viewDetails;
    public Button delete;
    public ListView<String> showData;

    private FinanceManagementSystem fms;
    private User user;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
        showAllIndividualPersons();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void createIndividualPerson() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../../FXML/individualPerson/AddIndividualPersonPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddIndividualPersonPageController addIndividualPersonPageController = loader.getController();
        addIndividualPersonPageController.setFms(fms);
        addIndividualPersonPageController.setUser(user);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateIndividualPerson() {
        if (getIndividualPersonFromSelection() == null) {
            return;
        }

        new LinksToPages().goToIndividualPersonUpdatePage(delete, fms, user, getIndividualPersonFromSelection());
    }

    private void showAllIndividualPersons() {
        fms.getIndividualPersons().forEach(individualPerson -> IndividualPersonList.getItems().add(
                individualPerson.getId() + " " + individualPerson.getName() + " " + individualPerson.getLoginName()));
    }

    public void viewIndividualPersonDetails() {
        showData.getItems().clear();

        User user = getIndividualPersonFromSelection();

        if (user != null) {
            showData.getItems().add(user.printIndividualPerson());
        }
    }

    private User getIndividualPersonFromSelection() {
        User user;

        if (IndividualPersonList.getSelectionModel().getSelectedItem() == null) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        String[] values = IndividualPersonList.getSelectionModel().getSelectedItem().split(" ");
        user = fms.getUserData(Integer.parseInt(values[0]));

        return user;
    }

    public void deleteIndividualPerson() {
        User user = getIndividualPersonFromSelection();

        assert user != null;
        userRepository.remove(user.getId());
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        new LinksToPages().goToIndividualPersonMainPage(viewDetails, fms, user);
    }

    public void returnToMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(viewDetails, fms, user);
    }
}