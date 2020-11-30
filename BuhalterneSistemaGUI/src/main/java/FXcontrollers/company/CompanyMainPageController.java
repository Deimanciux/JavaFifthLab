package FXcontrollers.company;

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

public class CompanyMainPageController extends AbstractController implements Initializable {
    public MenuItem create;
    public MenuItem update;
    public MenuItem goToMenu;
    public Button viewDetails;
    public ListView<String> showData;
    public Button delete;
    public ListView<String> companyList;

    private FinanceManagementSystem fms;
    private User user;
    private UserRepository userRepository;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
        showAllCompanies();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(entityManagerFactory);
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    }

    private void showAllCompanies() {
        fms.getCompanies().forEach(
                company -> companyList.getItems().add(company.getId() +
                        " " + company.getName() +
                        " " + company.getLoginName()));
    }

    public void createCompany() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../FXML/company/AddCompanyPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddCompanyPageController addCompanyPageController = loader.getController();
        addCompanyPageController.setFms(fms);
        addCompanyPageController.setUser(user);

        Stage stage = (Stage) delete.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateCompany() {
        if (getCompanyFromSelection() == null) {
            return;
        }

        new LinksToPages().goToCompanyUpdatePage(delete, fms, user, getCompanyFromSelection());
    }

    public void returnToMainMenuPage() throws IOException {
        new LinksToPages().returnToMainMenuPage(viewDetails, fms, user);
    }

    public void viewCompanyDetails() {
        User company;

        showData.getItems().clear();
        company = getCompanyFromSelection();
        if (company != null) {
            showData.getItems().add(company.printCompany());
        }
    }

    private User getCompanyFromSelection() {
        User company;
        String[] values;

        if (companyList.getSelectionModel().getSelectedItem() == null) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        values = companyList.getSelectionModel().getSelectedItem().split(" ");
        company = fms.getUserData(Integer.parseInt(values[0]));

        return company;
    }

    public void deleteCompany() {
        User user = getCompanyFromSelection();

        assert user != null;
        userRepository.remove(user.getId());
        fms = financeManagementSystemRepository.getFmsById(fms.getId());

        new LinksToPages().goToCompanyMainPage(viewDetails, fms, user);
    }
}