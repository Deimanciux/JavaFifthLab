package Utils;

import javafx.scene.control.Alert;

public class ErrorPrinter {
    public static void printError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(text);

        alert.showAndWait();
    }
}