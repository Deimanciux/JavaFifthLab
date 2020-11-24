package FXcontrollers.income;

import FXcontrollers.AbstractController;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.IncomeRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.Income;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddIncomePageController extends AbstractController implements Initializable {
    public TextField amount;
    public TextField incomeGiver;
    public Button addIncome;
    public TextArea description;
    public Button cancel;

    private FinanceManagementSystem fms;
    private User user;
    private Category category;
    private CategoryRepository categoryRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;
    private IncomeRepository incomeRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryRepository = new CategoryRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        incomeRepository = new IncomeRepository(entityManagerFactory);
    }

    public void addIncome() {
        Income income;

        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!amount.getText().matches(".*\\d.*")){
            ErrorPrinter.printError("Amount has to be numeric");
            return;
        }

        income = new Income(
                Double.parseDouble(amount.getText()),
                description.getText(),
                incomeGiver.getText(),
                LocalDateTime.now(),
                category
        );

        incomeRepository.create(income);
        category.getIncomes().add(income);
        categoryRepository.edit(category);
        category = categoryRepository.getCategoryById(category.getId());
        this.fms = financeManagementSystemRepository.getFmsById(this.fms.getId());

        returnToIncomeMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(amount.getText()) &&
                isNotEmpty(description.getText()) &&
                isNotEmpty(incomeGiver.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToIncomeMainPage() {
        new LinksToPages().goToIncomeMainPage(addIncome, fms, user, category);
    }
}