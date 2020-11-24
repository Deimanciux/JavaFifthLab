package FXcontrollers.category;

import FXcontrollers.AbstractController;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteResponsiblePeoplePageController extends AbstractController implements Initializable {
    public Button submit;
    public TextField userLogin;
    public Button cancel;
    public ChoiceBox<User> userChoice = new ChoiceBox<>();

    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private UserRepository userRepository;
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
        initializeUsersChoiceBox();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
    }

    public void DeleteResponsiblePerson() {
        if (category.getResponsiblePerson().size() <= 1) {
            ErrorPrinter.printError("It's only one responsible person. You can't delete it");
            return;
        }

        User user = userChoice.getSelectionModel().getSelectedItem();
        user.removeCategoryById(category.getId());
        userRepository.edit(user);

        returnToCategoryMainPage();
    }

    private void initializeUsersChoiceBox() {
        userList.addAll(category.getResponsiblePerson());
        userChoice.setItems(userList);
    }

    public void returnToCategoryMainPage() {
        new LinksToPages().goToManageCategoriesPage(submit, fms, user);
    }
}