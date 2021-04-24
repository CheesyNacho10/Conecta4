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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Connect4;
import util.Navigation;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLForgetPassController implements Initializable {

    @FXML
    private Button BSendPass;
    
    @FXML
    private Text TError;
    
    @FXML
    private TextField TFUsername;
    
    private Connect4 db;
    private ApplicationState applicationState;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDB();
        initAppState();
    }    
    
    @FXML
    private void recoverPassword() {
        if(db.exitsNickName(TFUsername.getText())) {
            TError.setVisible(false);
            showInfoDialog();
            applicationState.setForgotPassNickName(TFUsername.getText());
            Navigation.navigateToConfirmCode((Stage) BSendPass.getScene().getWindow(), getClass());
        } else {
            TError.setVisible(true);
        }
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
    
    private void showInfoDialog() {
        Alert dialog = new Alert(AlertType.INFORMATION);
        dialog.setTitle("Email");
        dialog.setHeaderText("Correo electrónico ha sido mandado");
        dialog.setContentText("Tu código es 111");
        dialog.showAndWait();
    }
    
}
