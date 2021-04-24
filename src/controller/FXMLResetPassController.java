/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import application.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;
import util.Navigation;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLResetPassController implements Initializable {

    
    @FXML
    private Text TPass;
    
    private Connect4 db;
    private ApplicationState applicationState;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDB();
        initAppState();
        setText();
    }    
    
    @FXML
    private void navigateToIni() {
        Navigation.navigateToIni((Stage) TPass.getScene().getWindow(), getClass());
    }
    
    private void setText() {
        Player p = db.getPlayer(applicationState.getForgotPassNickName());
        TPass.setText(p.getPassword());
    }
    
    private void initDB() {
        try {
            db = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException err) {
            System.out.println(err);
        }
    }
    
    private void initAppState() {
        applicationState = ApplicationState.getInstance();
    }
    
}
