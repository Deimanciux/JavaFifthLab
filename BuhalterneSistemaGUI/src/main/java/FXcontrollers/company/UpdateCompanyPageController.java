package FXcontrollers.company;

import FXcontrollers.AbstractController;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCompanyPageController extends AbstractController implements Initializable {
    public TextField name;
    public TextField loginName;
    public TextField phoneNumber;
    public TextField email;
    public TextField contactPersonSurname;
    public PasswordField password;
    public TextField contactPersonName;
    public Button updateCompany;
    public Button cancel;

    private FinanceManagementSystem fms;
    private User user;
    private User company;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCompany(User company) {
        this.company = company;
        name.setText(company.getName());
        loginName.setText((company.getLoginName()));
        phoneNumber.setText(company.getPhoneNumber());
        email.setText(company.getPhoneNumber());
        contactPersonSurname.setText(company.getContactPersonSurname());
        contactPersonName.setText(company.getContactPersonName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    public void updateCompany() {
        if (!checkIfFieldsAreEmpty()) {
            ErrorPrinter.printError("Fields can not be empty");
            return;
        }

        if (!password.getText().equals("")) {
            company.setPassword(password.getText());
        }

        company.setName(name.getText());
        company.setLoginName(loginName.getText());
        company.setEmail(email.getText());
        company.setContactPersonName(contactPersonName.getText());
        company.setContactPersonSurname(contactPersonSurname.getText());
        company.setPhoneNumber(phoneNumber.getText());


        try {
            userRepository.edit(company);
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
        } catch (Exception e) {
            ErrorPrinter.printError("This login name is taken. Choose another one.");
            fms = financeManagementSystemRepository.getFmsById(fms.getId());
            return;
        }

        returnToCompanyMainPage();
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText()) &&
                isNotEmpty(loginName.getText()) &&
                isNotEmpty(phoneNumber.getText()) &&
                isNotEmpty(email.getText()) &&
                isNotEmpty(contactPersonName.getText()) &&
                isNotEmpty(contactPersonSurname.getText());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void returnToCompanyMainPage() {
        new LinksToPages().goToCompanyMainPage(updateCompany, fms, user);
    }
}