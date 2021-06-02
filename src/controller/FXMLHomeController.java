/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import credentials.ApplicationState;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Player;
import nav.Navigation;

/**
 * Home screen controller where the player can choose between
 * playing against the second player or against the machine.
 * The second player can also log in or register
 * @author 44puk
 */
public class FXMLHomeController extends FXMLBaseController {
    
    @FXML
    private ImageView IVFirstPlayerAvatar;
    
    @FXML
    private Text TFirstPlayerName;
    
    @FXML
    private ImageView IVSecondPlayerAvatar;
    
    @FXML
    private Text TSecondPlayerName;
    
    @FXML
    private Button BSecondPlayerAction;
    
    @FXML
    private Button BPvP;
    
    @FXML
    private Button BPvE;
    @FXML
    private Button BSecondPlayerEdit;
    
    /**
     * Initializes the two players
     */
    @Override
    public void init() {
        initFirstPlayer();
        initSecondPlayer();
        initButtons();
    } 
    
    @FXML
    private void firstPlayerLogOut() {
        if(applicationState.logOutFirstPlayer()) {
            // Another player is still logged in so he is player 1 now
            initFirstPlayer();
            initSecondPlayer();
            initButtons();
        } else {
            // No more logged in player, so go to log in screen
            Navigation.navigateToSignIn((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
        }
    }
    
    @FXML
    private void secondPlayerAction() {
        Player secondPlayer = applicationState.getSecondPlayer();
        if(secondPlayer != null) {
            applicationState.logOutSecondPlayer();
            initSecondPlayer();
            initButtons();
        } else {
            Navigation.navigateToSignIn((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
        }
    }
    
    @FXML
    private void navigateToGame() {
        Navigation.navigateToGame((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void navigateToRanking() {
        Navigation.navigateToRanking((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void navigateToHistory() {
        Navigation.navigateToHistory((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void navigateToStatistics() {
        showDatePickerDialog();
        Navigation.navigateToStatistics((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void toggleStyle() {
        Navigation.isDark = !Navigation.isDark;
        Navigation.navigateToHome((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }
   
    private void initSecondPlayer() {
        Player secondPlayer = applicationState.getSecondPlayer();
        if(secondPlayer != null) {
            IVSecondPlayerAvatar.setVisible(true);
            IVSecondPlayerAvatar.setImage(secondPlayer.getAvatar());
            TSecondPlayerName.setText(secondPlayer.getNickName());
            BSecondPlayerAction.setText("Salir");
            BSecondPlayerEdit.setText("Editar perfil");
        } else {
            IVSecondPlayerAvatar.setVisible(false);
            TSecondPlayerName.setText("Jugador 2");
            BSecondPlayerAction.setText("Iniciar sesión");
            BSecondPlayerEdit.setText("Registrarse");
        }
        
    }
    
    private void initFirstPlayer() {
        Player firstPlayer = applicationState.getFirstPlayer();
        IVFirstPlayerAvatar.setImage(firstPlayer.getAvatar());
        TFirstPlayerName.setText(firstPlayer.getNickName());
    }    

    private void initButtons() {
        if(applicationState.getSecondPlayer() != null) {
            BPvE.disableProperty().setValue(true);
            BPvP.disableProperty().setValue(false);
        } else {
            BPvE.disableProperty().setValue(false);
            BPvP.disableProperty().setValue(true);
        }
    }

    @FXML
    private void firstPlayerEdit(ActionEvent event) {
        applicationState.setShouldEditFirstPlayer(true);
        Navigation.navigateToSignUp((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
    }

    @FXML
    private void secondPlayerEdit(ActionEvent event) {
        applicationState.setShouldEditFirstPlayer(false);
        Navigation.navigateToSignUp((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
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
        
        DialogPane dialogP = dialog.getDialogPane();
        String style = Navigation.isDark ? "/view/styleB.css" : "/view/style.css";
        dialogP.getStylesheets().add(getClass().getResource(style).toExternalForm());
        
        Optional<Pair<LocalDate, LocalDate>> result = dialog.showAndWait();

        result.ifPresent(startAndEndDates -> {
            applicationState.setStartDate(startAndEndDates.getKey());
            applicationState.setEndDate(startAndEndDates.getValue());
        });
    }
}
