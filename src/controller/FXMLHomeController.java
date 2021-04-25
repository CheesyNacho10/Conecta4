/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import util.Navigation;

/**
 *
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
    private Button BSecondPlayerButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {
        initFirstPlayer();
        initSecondPlayer();
    } 
    
    @FXML
    private void firstPlayerLogOut() {
        if(applicationState.logOutFirstPlayer()) {
            // another player is still logged in so he is player 1 now
            initFirstPlayer();
            initSecondPlayer();
        } else {
            // no more logged in player, go to log in screen
            Navigation.navigateToSignIn((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
        }
    }
    
    @FXML
    private void secondPlayerAction() {
        Player secondPlayer = applicationState.getSecondPlayer();
        if(secondPlayer != null) {
            applicationState.logOutSecondPlayer();
            initSecondPlayer();
        } else {
            Navigation.navigateToSignIn((Stage) IVFirstPlayerAvatar.getScene().getWindow(), getClass());
        }
    }
   
    private void initSecondPlayer() {
        
        Player secondPlayer = applicationState.getSecondPlayer();
        if(secondPlayer != null) {
            IVSecondPlayerAvatar.setVisible(true);
            IVSecondPlayerAvatar.setImage(secondPlayer.getAvatar());
            TSecondPlayerName.setText(secondPlayer.getNickName());
            BSecondPlayerButton.setText("Salir");
        } else {
            IVSecondPlayerAvatar.setVisible(false);
            TSecondPlayerName.setText("Jugador 2");
            BSecondPlayerButton.setText("Iniciar sesión");
        }
        
    }
    
    private void initFirstPlayer() {
        Player firstPlayer = applicationState.getFirstPlayer();
        IVFirstPlayerAvatar.setImage(firstPlayer.getAvatar());
        TFirstPlayerName.setText(firstPlayer.getNickName());
    }    
}
