/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import credentials.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
        Navigation.navigateToSignUp((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass(), applicationState.getFirstPlayer());
    }

    @FXML
    private void secondPlayerEdit(ActionEvent event) {
        Navigation.navigateToSignUp((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass(), applicationState.getSecondPlayer());
    }
}
