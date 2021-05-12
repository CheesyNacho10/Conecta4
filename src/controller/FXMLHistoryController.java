/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import nav.Navigation;

/**
 *
 * @author 44puk
 */
public class FXMLHistoryController extends FXMLBaseController {
    
    @FXML
    private Button button;

    @Override
    void init() {
    }
    
    @FXML
    private void navigateToHome() {
        Navigation.navigateToHome((Stage) button.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void navigateToTotalHistory() {
        showDatePickerDialog();
        Navigation.navigateToTotalHistory((Stage) button.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void navigateToPlayerHistory() {
        showDatePickerDialog();
        Navigation.navigateToHome((Stage) button.getScene().getWindow(), getClass());
    }
    
    private void showDatePickerDialog() {
        // Create the custom dialog.
        Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");
        
        ButtonType okButton = ButtonType.OK;
        
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker start = new DatePicker(LocalDate.now());
        DatePicker end = new DatePicker(LocalDate.now());

        grid.add(new Label("Desde:"), 0, 0);
        grid.add(start, 1, 0);
        grid.add(new Label("Hasta:"), 0, 1);
        grid.add(end, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Pair<>(start.getValue(), end.getValue());
            }
            return null;
        });

        Optional<Pair<LocalDate, LocalDate>> result = dialog.showAndWait();

        result.ifPresent(startAndEndDates -> {
            applicationState.setStartDate(startAndEndDates.getKey());
            applicationState.setEndDate(startAndEndDates.getValue());
        });
    }
    
}
