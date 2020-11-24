package FXcontrollers.category;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCategoryPageController extends AbstractController implements Initializable {

    public Button submit;
    public TextArea description;
    public Button cancel;
    public TextField name;

    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private CategoryRepository categoryRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
        name.setText(category.getName());
        description.setText(category.getDescription());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryRepository = new CategoryRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void updateCategory() {
        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        category.setName(name.getText());
        category.setDescription(description.getText());

        categoryRepository.edit(category);
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