/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import model.ApplicationState;
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
public class FXMLResetPassController extends FXMLBaseController {
    
    @FXML
    private Text TPass;
    
    @FXML
    private void navigateToIni() {
        Navigation.navigateToIni((Stage) TPass.getScene().getWindow(), getClass());
    }
    
    private void setText() {
        Player p = db.getPlayer(applicationState.getForgotPassNickName());
        TPass.setText(p.getPassword());
    }

    @Override
    void init() {
        setText();
    }
    
}
