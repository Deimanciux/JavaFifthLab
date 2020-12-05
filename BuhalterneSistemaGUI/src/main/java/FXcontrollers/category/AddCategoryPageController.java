package FXcontrollers.category;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryPageController extends AbstractController implements Initializable {
    public TextField name;
    public Button submit;
    public TextArea description;
    public ChoiceBox<String> parentCategoryChoice = new ChoiceBox<>();
    public Button cancel;

    private FinanceManagementSystem fms;
    private User user;
    private final ObservableList<String> parentCategoryList = FXCollections.observableArrayList();
    private CategoryRepository categoryRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private UserRepository userRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
        initializeParentCategoryChoiceBox();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryRepository = new CategoryRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        userRepository = new UserRepository(entityManagerFactory);
    }

    private void initializeParentCategoryChoiceBox() {
        parentCategoryChoice.getItems().clear();

        for (Category category : fms.getCategories()) {
            parentCategoryList.add(category.getName());
        }
        parentCategoryList.add("none");
        parentCategoryChoice.setValue("none");
        parentCategoryChoice.setItems(parentCategoryList);
    }

    public void addCategory() {
        Category category;
        String parentName;
        Category parentCategory = null;

        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        parentName = parentCategoryChoice.getValue();

        if (!parentName.equals("none")) {
            parentCategory = fms.getCategoryData(parentName);
        }

        category = new Category(
                name.getText(),
                description.getText(),
                parentCategory,
                fms
        );

        categoryRepository.create(category);
        fms = financeManagementSystemRepository.getFmsById(fms.getId());
        Category category1 = categoryRepository.getCategoryLastInsertedRecord();

        user.addCategory(category1);
        userRepository.edit(user);
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        returnToCategoryMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(description.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToCategoryMainPage() {
        new LinksToPages().goToManageCategoriesPage(submit, fms, user);
    }
}