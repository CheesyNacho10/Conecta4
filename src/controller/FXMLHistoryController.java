/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Paint;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
        showPlayerAndDatePickerDialog();
        Navigation.navigateToPlayerHistory((Stage) button.getScene().getWindow(), getClass());
    }
    
    private void showDatePickerDialog() {
        // Create the custom dialog.
        Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
        dialog.setTitle("Elegir fechas");
        dialog.setHeaderText("Elija el periodo de tiempo para mostrar las partidas");
        
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
    
    private void showPlayerAndDatePickerDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, Pair<LocalDate, LocalDate>>> dialog = new Dialog<>();
        dialog.setTitle("Elegir fechas y usuario");
        dialog.setHeaderText("Elija el periodo de tiempo y el usuario para mostrar las partidas");
        
        ButtonType okButton = ButtonType.OK;
        
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker start = new DatePicker(LocalDate.now());
        DatePicker end = new DatePicker(LocalDate.now());
        
        TextField textField = new TextField();
        textField.setPromptText("Entra el nombre del jugador");
        
        Text error = new Text();
        error.setText("Jugador no existe");
        error.setFill(Color.RED);
        
        grid.add(new Label("Jugador:"), 0, 0);
        grid.add(textField, 1, 0);
        grid.add(error, 2, 0);
        grid.add(new Label("Desde:"), 0, 1);
        grid.add(start, 1, 1);
        grid.add(new Label("Hasta:"), 0, 2);
        grid.add(end, 1, 2);

        dialog.getDialogPane().setContent(grid);
        
        Node buttonNode = dialog.getDialogPane().lookupButton(okButton);
        
        buttonNode.setDisable(true);
        error.setVisible(false);
        
        textField.setOnKeyReleased(e -> {
            
            boolean userExists = db.exitsNickName(textField.getText());
            
            buttonNode.setDisable(!userExists);
            error.setVisible(!userExists);
            
        });
        
        dialog.setResultConverter(dialogButton -> {
            
            if (dialogButton == okButton) {
                
                Pair<LocalDate, LocalDate> dates = new Pair<>(start.getValue(), end.getValue());
                
                return new Pair(textField.getText(), dates);
            }
            return null;
        });

        Optional<Pair<String, Pair<LocalDate, LocalDate>>> result = dialog.showAndWait();

        result.ifPresent(nickNameAndDates -> {
            
            String nickName = nickNameAndDates.getKey();
            Pair<LocalDate, LocalDate> dates = nickNameAndDates.getValue();
            
            applicationState.setPlayerToShowHistory(nickName);
            applicationState.setStartDate(dates.getKey());
            applicationState.setEndDate(dates.getValue());
        });
    }
    
}
