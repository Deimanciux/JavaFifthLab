package FXcontrollers.income;

import Utils.Constants;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.Category;
import dataStructures.Income;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateIncomePageController implements Initializable {
    public TextField amount;
    public TextField incomeGiver;
    public Button updateIncome;
    public TextArea description;
    public Button cancel;
    public TextField date;

    private FinanceManagementSystem fms;
    private User user;
    private Income income;
    private Category category;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIncome(Income income) {
        this.income = income;
        amount.setText(String.valueOf(income.getAmount()));
        incomeGiver.setText(income.getIncomeGiver());
        description.setText(income.getDescription());
        date.setText(income.getDate().format(Constants.DATE_FORMAT));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void updateIncome() {
        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!amount.getText().matches(".*\\d.*")){
            ErrorPrinter.printError("Amount has to be numeric");
            return;
        }

        income.setDate(LocalDateTime.parse(date.getText(), Constants.DATE_FORMAT));
        income.setAmount(Double.parseDouble(amount.getText()));
        income.setDescription(description.getText());
        income.setIncomeGiver(incomeGiver.getText());

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
        new LinksToPages().goToIncomeMainPage(updateIncome, fms, user, category);
    }
}