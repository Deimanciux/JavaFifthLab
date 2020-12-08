package FXcontrollers;

import HibernateRepository.FinanceManagementSystemRepository;
import Utils.ErrorPrinter;
import Utils.LinksToPages;
import dataStructures.FinanceManagementSystem;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FmsPageController extends AbstractController implements Initializable {
    public ListView<FinanceManagementSystem> fmsListView;
    public Label title;
    public Button button;
    private FinanceManagementSystemRepository financeManagementSystemRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        button.setVisible(false);
        showAllFms();
    }

    private void showAllFms() {
        fmsListView.getItems().clear();

        List<FinanceManagementSystem> allFms = financeManagementSystemRepository.getAllFms();
        allFms.forEach(fms -> fmsListView.getItems().add(fms));
    }

    public void goToLoginPage() {
        if (fmsListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        System.out.println(getFinanceManagementSystemFromSelection());
        new LinksToPages().goToLoginPage(button, getFinanceManagementSystemFromSelection());
    }

    private FinanceManagementSystem getFinanceManagementSystemFromSelection() {
        if (fmsListView.getSelectionModel().getSelectedItem() == null) {
            ErrorPrinter.printError("No fields was selected");
            return null;
        }

        return fmsListView.getSelectionModel().getSelectedItem();
    }
}