/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLFinalStateController extends FXMLBaseController {

    @FXML
    private Text TFinalState;
    @FXML
    private Button BPlayAgain;
    @FXML
    private Button BGoHome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {
        
    }
    
    public void setText(String s) {
        TFinalState.setText(s);
    }

    @FXML
    private void playAgain(ActionEvent event) {
    }

    @FXML
    private void goHome(ActionEvent event) {
    }
}
