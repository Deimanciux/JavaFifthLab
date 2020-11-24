package FXcontrollers.category;

import FXcontrollers.AbstractController;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
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

public class AddResponsiblePeoplePageController extends AbstractController implements Initializable {
    public Button submit;
    public TextField userLogin;
    public Button cancel;
    public ChoiceBox<User> userChoice = new ChoiceBox<>();

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

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
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void addResponsiblePerson() {

        User user = userChoice.getSelectionModel().getSelectedItem();
        user.addCategory(category);
        userRepository.edit(user);
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        returnToCategoryMainPage();
    }

    private void initializeUsersChoiceBox() {
        System.out.println(fms.getUsers());
        System.out.println(category);
        for (User user : fms.getUsers()) {
            if (!checkIfUserExistsInCategory(user)) {
                userList.add(user);
            }
        }

        userChoice.setItems(userList);
    }

    public boolean checkIfUserExistsInCategory(User searchUser) {
        for (User user : category.getResponsiblePerson()) {
            if (user.getId() == searchUser.getId()) {
                return true;
            }
        }

        return false;
    }

    public void returnToCategoryMainPage() {
        new LinksToPages().goToManageCategoriesPage(submit, fms, user);
    }
}